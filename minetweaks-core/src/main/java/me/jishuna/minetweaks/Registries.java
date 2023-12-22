package me.jishuna.minetweaks;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class Registries {
    public static final TweakRegistry TWEAKS;
    public static final Registry<NamespacedKey, ItemStack> ITEMS;

    static {
        ITEMS = new Registry<>();
        TWEAKS = new TweakRegistry();
    }

    private Registries() {
    }
}
