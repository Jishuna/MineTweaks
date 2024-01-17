package me.jishuna.minetweaks.tweak;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import me.jishuna.jishlib.ClassScanner;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.MineTweaks;

public class TweakRegistry extends Registry<String, Tweak> {
    private final Map<PacketType, List<PacketTweak>> tweaksByPacket = new HashMap<>();
    private final Set<TickingTweak> tickingTweaks = new HashSet<>();
    private final Set<ToggleableTweak> toggleableTweaks = new TreeSet<>(Comparator.comparing(Tweak.class::cast));
    private final Plugin plugin;

    public TweakRegistry(Plugin plugin) {
        this.plugin = plugin;

        new ClassScanner<>(this.getClass().getClassLoader(), Tweak.class, RegisterTweak.class)
                .forEach((Consumer<Tweak>) this::register);

        reload();
    }

    public void reload() {
        this.tweaksByPacket.clear();
        this.tickingTweaks.clear();
        this.toggleableTweaks.clear();

        getValues().forEach(tweak -> {
            tweak.reload();

            if (tweak instanceof ToggleableTweak toggle && tweak.isEnabled()) {
                this.toggleableTweaks.add(toggle);
            }

            setupEvents(tweak);
            setupTicking(tweak);

            if (MineTweaks.hasPackets()) {
                setupPackets(tweak);
            }
        });
    }

    public void register(Tweak tweak) {
        register(tweak.getName(), tweak, true);
    }

    public void processPacket(PacketEvent event, PacketContainer packet) {
        List<PacketTweak> tweaks = this.tweaksByPacket.get(packet.getType());
        if (tweaks != null) {
            tweaks.forEach(tweak -> tweak.handlePacket(event, packet));
        }
    }

    public void tick() {
        this.tickingTweaks.forEach(TickingTweak::tick);
    }

    private void setupEvents(Tweak tweak) {
        HandlerList.unregisterAll(tweak);

        if (tweak.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(tweak, this.plugin);
        }
    }

    private void setupTicking(Tweak tweak) {
        if (!(tweak instanceof TickingTweak ticking) || !tweak.isEnabled()) {
            return;
        }

        this.tickingTweaks.add(ticking);
    }

    private void setupPackets(Tweak tweak) {
        if (!(tweak instanceof PacketTweak packetTweak) || !tweak.isEnabled()) {
            return;
        }

        for (PacketType type : packetTweak.getListenedPackets()) {
            this.tweaksByPacket.computeIfAbsent(type, k -> new ArrayList<>()).add(packetTweak);
        }
    }

    public Set<ToggleableTweak> getToggleableTweaks() {
        return this.toggleableTweaks;
    }
}
