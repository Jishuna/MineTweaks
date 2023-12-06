package me.jishuna.minetweaks.tweak;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.event.Event;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.tweak.armorstand.ArmorStandArmsTweak;
import me.jishuna.minetweaks.tweak.block.BeehiveDisplayTweak;
import me.jishuna.minetweaks.tweak.block.FastLeafDecayTweak;
import me.jishuna.minetweaks.tweak.block.OpenThroughItemFramesTweak;
import me.jishuna.minetweaks.tweak.block.OpenThroughSignsTweak;
import me.jishuna.minetweaks.tweak.block.PaintingSelectorTweak;
import me.jishuna.minetweaks.tweak.block.QuickShulkerBoxCreationTweak;
import me.jishuna.minetweaks.tweak.block.StickyPistonConversionTweak;
import me.jishuna.minetweaks.tweak.crafting.CustomCraftingRecipesTweak;
import me.jishuna.minetweaks.tweak.crafting.UnlockAllRecipesTweak;
import me.jishuna.minetweaks.tweak.item.InfiniteWaterBucketTweak;
import me.jishuna.minetweaks.tweak.mob.EndermanGriefingTweak;
import me.jishuna.minetweaks.tweak.mob.HorseStatsTweak;
import me.jishuna.minetweaks.tweak.mob.PoisonPotatoBabyMobsTweak;
import me.jishuna.minetweaks.tweak.mob.UnsaddlePigTweak;

public class TweakRegistry extends Registry<String, Tweak> {
    private final Map<Class<? extends Event>, TweakList<Tweak>> tweaksByEvent = new HashMap<>();
    private final Map<PacketType, TweakList<PacketTweak>> tweaksByPacket = new HashMap<>();
    private final Set<TickingTweak> tickingTweaks = new HashSet<>();

    public TweakRegistry() {
        register(new ArmorStandArmsTweak());
        register(new CustomCraftingRecipesTweak());
        register(new UnsaddlePigTweak());
        register(new UnlockAllRecipesTweak());
        register(new StickyPistonConversionTweak());
        register(new PaintingSelectorTweak());
        register(new BeehiveDisplayTweak());
        register(new InfiniteWaterBucketTweak());
        register(new EndermanGriefingTweak());
        register(new PoisonPotatoBabyMobsTweak());
        register(new HorseStatsTweak());
        register(new FastLeafDecayTweak());
        register(new QuickShulkerBoxCreationTweak());
        register(new OpenThroughSignsTweak());
        register(new OpenThroughItemFramesTweak());
    }

    public void register(Tweak tweak) {
        register(tweak.getName(), tweak, true);
    }

    @Override
    public void register(String key, Tweak tweak, boolean replace) {
        tweak.load();
        super.register(key, tweak, true);

        setupEvents(tweak);
        setupTicking(tweak);

        if (MineTweaks.hasPackets()) {
            setupPackets(tweak);
        }
    }

    public void processEvent(EventContext<?> context) {
        TweakList<Tweak> list = this.tweaksByEvent.get(context.getEvent().getClass());
        if (list == null) {
            return;
        }

        list.forEach(tweak -> tweak.handleEvent(context));
    }

    public void processPacket(PacketEvent event, PacketContainer packet) {
        TweakList<PacketTweak> list = this.tweaksByPacket.get(packet.getType());
        if (list == null) {
            return;
        }

        list.forEach(tweak -> tweak.handlePacket(event, packet));
    }

    public void tick() {
        this.tickingTweaks.forEach(TickingTweak::tick);
    }

    private void setupEvents(Tweak tweak) {
        for (Class<? extends Event> clazz : tweak.getListenedEvents()) {
            if (tweak.isEnabled()) {
                this.tweaksByEvent.computeIfAbsent(clazz, k -> new TweakList<>()).addOrReplace(tweak);
            } else {
                TweakList<Tweak> list = this.tweaksByEvent.get(clazz);
                if (list != null) {
                    list.remove(tweak);
                }
            }
        }
    }

    private void setupTicking(Tweak tweak) {
        if (!(tweak instanceof TickingTweak ticking)) {
            return;
        }

        this.tickingTweaks.remove(ticking);
        this.tickingTweaks.add(ticking);
    }

    private void setupPackets(Tweak tweak) {
        if (!(tweak instanceof PacketTweak packetTweak)) {
            return;
        }

        for (PacketType type : packetTweak.getListenedPackets()) {
            if (packetTweak.isEnabled()) {
                this.tweaksByPacket.computeIfAbsent(type, k -> new TweakList<>()).addOrReplace(packetTweak);
            } else {
                TweakList<PacketTweak> list = this.tweaksByPacket.get(type);
                if (list != null) {
                    list.remove(packetTweak);
                }
            }
        }
    }
}
