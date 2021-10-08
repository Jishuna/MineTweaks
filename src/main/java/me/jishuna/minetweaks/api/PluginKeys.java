package me.jishuna.minetweaks.api;

import org.bukkit.NamespacedKey;

import me.jishuna.minetweaks.MineTweaks;

public enum PluginKeys {
	SKELETONS_SPAWNED("skeletons_spawned");

	private final NamespacedKey key;

	private PluginKeys(String name) {
		this.key = new NamespacedKey(MineTweaks.getPlugin(MineTweaks.class), name);
	}

	public NamespacedKey getKey() {
		return this.key;
	}

}
