package me.jishuna.minetweaks;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class Registries {
    public static TweakRegistry TWEAK;
    public static Registry<NamespacedKey, ItemStack> ITEM;

    public static void initialize(MineTweaks plugin) {
        ITEM = new Registry<>();
        TWEAK = new TweakRegistry(plugin);
    }

    private Registries() {
    }
}
