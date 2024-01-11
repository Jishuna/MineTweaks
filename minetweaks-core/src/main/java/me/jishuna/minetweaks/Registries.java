package me.jishuna.minetweaks;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.datastructure.Registry;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class Registries {
    public static final TweakRegistry TWEAK;
    public static final Registry<NamespacedKey, ItemStack> ITEM;

    static {
        ITEM = new Registry<>();
        TWEAK = new TweakRegistry();
    }

    private Registries() {
    }
}
