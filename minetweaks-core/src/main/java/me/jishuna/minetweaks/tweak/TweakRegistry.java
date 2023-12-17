package me.jishuna.minetweaks.tweak;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.event.Event;
import me.jishuna.jishlib.datastructure.Registry;
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
import me.jishuna.minetweaks.tweak.dispenser.DispenserPlaceBlocksTweak;
import me.jishuna.minetweaks.tweak.farming.CactusBonemealTweak;
import me.jishuna.minetweaks.tweak.farming.RightClickHarvestTweak;
import me.jishuna.minetweaks.tweak.farming.SugarcaneBonemealTweak;
import me.jishuna.minetweaks.tweak.item.ElytraTakeoffTweak;
import me.jishuna.minetweaks.tweak.item.InfiniteWaterBucketTweak;
import me.jishuna.minetweaks.tweak.item.TorchArrowTweak;
import me.jishuna.minetweaks.tweak.mob.EndermanGriefingTweak;
import me.jishuna.minetweaks.tweak.mob.HorseStatsTweak;
import me.jishuna.minetweaks.tweak.mob.PoisonPotatoBabyMobsTweak;
import me.jishuna.minetweaks.tweak.mob.UnsaddlePigTweak;

public class TweakRegistry extends Registry<String, Tweak> {
    private final Map<Class<? extends Event>, List<Tweak>> tweaksByEvent = new HashMap<>();
    private final Map<PacketType, List<PacketTweak>> tweaksByPacket = new HashMap<>();
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
        register(new CactusBonemealTweak());
        register(new SugarcaneBonemealTweak());
        register(new DispenserPlaceBlocksTweak());
        register(new TorchArrowTweak());
        register(new RightClickHarvestTweak());
        register(new ElytraTakeoffTweak());
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

    public void processEvent(Event event) {
        List<Tweak> tweaks = this.tweaksByEvent.get(event.getClass());
        if (tweaks != null) {
            tweaks.forEach(tweak -> tweak.handleEvent(event));
        }
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
        if (tweak.isEnabled()) {
            for (Class<? extends Event> clazz : tweak.getEventClasses()) {
                this.tweaksByEvent.computeIfAbsent(clazz, k -> new ArrayList<>()).add(tweak);
            }
        }
    }

    private void setupTicking(Tweak tweak) {
        if (!(tweak instanceof TickingTweak ticking)) {
            return;
        }

        this.tickingTweaks.add(ticking);
    }

    private void setupPackets(Tweak tweak) {
        if (!(tweak instanceof PacketTweak packetTweak)) {
            return;
        }

        if (packetTweak.isEnabled()) {
            for (PacketType type : packetTweak.getListenedPackets()) {
                if (packetTweak.isEnabled()) {
                    this.tweaksByPacket.computeIfAbsent(type, k -> new ArrayList<>()).add(packetTweak);
                }
            }
        }
    }
}
