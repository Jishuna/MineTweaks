package me.jishuna.minetweaks.api;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.MineTweaks;

public enum PluginKeys {
	SKELETONS_SPAWNED("skeletons_spawned"), ITEM_TYPE("item_type");

	private final NamespacedKey key;

	private PluginKeys(String name) {
		this.key = new NamespacedKey(JavaPlugin.getPlugin(MineTweaks.class), name);
	}

	public NamespacedKey getKey() {
		return this.key;
	}

}
