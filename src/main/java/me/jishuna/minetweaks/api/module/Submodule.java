package me.jishuna.minetweaks.api.module;

import org.bukkit.configuration.file.YamlConfiguration;

public class Submodule {

	private final String key;
	private String name;
	private String description;

	public Submodule(String key) {
		this.key = key;
	}

	public void loadInformation(YamlConfiguration config) {
		this.name = config.getString("submodules." + this.key + ".name");
		this.description = config.getString("submodules." + this.key + ".description");
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
