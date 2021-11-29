package me.jishuna.minetweaks.api.tweak;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.jishuna.minetweaks.api.events.EventWrapper;

public abstract class Tweak {
	private final String name;
	private final JavaPlugin owningPlugin;

	private String displayName;
	private String description;
	private String category;
	private boolean enabled;
	
	private final Set<String> invalidVersions = new HashSet<>();

	private final Multimap<Class<? extends Event>, EventWrapper<? extends Event>> handlerMap = ArrayListMultimap
			.create();

	public Tweak(JavaPlugin plugin, String name) {
		this.name = name;
		this.owningPlugin = plugin;
	}

	public abstract void reload();

	public void loadDefaults(ConfigurationSection section, boolean def) {
		this.enabled = section.getBoolean("enabled", def);
		this.displayName = section.getString("display-name");
		this.description = section.getString("description");
		this.category = section.getString("category", "None");
	}

	public Set<Class<? extends Event>> getEventClasses() {
		return this.handlerMap.keySet();
	}

	public <T extends Event> void addEventHandler(Class<T> type, Consumer<T> consumer) {
		this.handlerMap.put(type, new EventWrapper<>(type, consumer));
	}

	public <T extends Event> Collection<EventWrapper<? extends Event>> getEventHandlers(Class<T> type) {
		return this.handlerMap.get(type);
	}
	
	public boolean isVersionValid(String version) {
		return !this.invalidVersions.contains(version);
	}
	 
	public void addInvalidVersions(String... versions) {
		this.invalidVersions.addAll(Arrays.asList(versions));
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public JavaPlugin getOwningPlugin() {
		return owningPlugin;
	}
}
