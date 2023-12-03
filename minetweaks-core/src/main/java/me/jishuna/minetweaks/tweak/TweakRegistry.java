package me.jishuna.minetweaks.tweak;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Event;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.armorstand.ArmorStandArmsTweak;
import me.jishuna.minetweaks.tweak.block.BeehiveDisplayTweak;
import me.jishuna.minetweaks.tweak.block.PaintingSelectorTweak;
import me.jishuna.minetweaks.tweak.block.StickyPistonConversionTweak;
import me.jishuna.minetweaks.tweak.crafting.CustomCraftingRecipesTweak;
import me.jishuna.minetweaks.tweak.crafting.UnlockAllRecipesTweak;
import me.jishuna.minetweaks.tweak.item.InfiniteWaterBucketTweak;
import me.jishuna.minetweaks.tweak.mob.EndermanGriefingTweak;
import me.jishuna.minetweaks.tweak.mob.PoisonPotatoBabyMobsTweak;
import me.jishuna.minetweaks.tweak.mob.UnsaddlePigTweak;

public class TweakRegistry extends Registry<String, Tweak> {
    private final Map<Class<? extends Event>, TweakList> tweaksByEvent = new HashMap<>();

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
    }

    public void register(Tweak tweak) {
        register(tweak.getName(), tweak, true);
    }

    @Override
    public void register(String key, Tweak tweak, boolean replace) {
        tweak.load();

        super.register(key, tweak, true);

        for (Class<? extends Event> clazz : tweak.getListenedEvents()) {
            if (tweak.isEnabled()) {
                this.tweaksByEvent.computeIfAbsent(clazz, k -> new TweakList()).addOrReplace(tweak);
            } else {
                TweakList list = this.tweaksByEvent.get(clazz);
                if (list != null) {
                    list.remove(tweak);
                }
            }
        }
    }

    public void process(EventContext<?> context) {
        TweakList list = this.tweaksByEvent.get(context.getEvent().getClass());
        if (list == null) {
            return;
        }

        for (Tweak tweak : list) {
            tweak.handleEvent(context);
        }
    }
}
