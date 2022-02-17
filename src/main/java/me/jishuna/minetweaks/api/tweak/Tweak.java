package me.jishuna.minetweaks.api.tweak;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.events.EventWrapper;

public abstract class Tweak {
	private final String name;
	private final MineTweaks plugin;

	private String displayName;
	private String description;
	private String category;
	private boolean enabled;

	private final Set<String> invalidVersions = new HashSet<>();

	private final Table<Class<? extends Event>, EventPriority, EventWrapper<? extends Event>> eventTable = HashBasedTable
			.create();

	public Tweak(MineTweaks plugin, String name) {
		this.name = name;
		this.plugin = plugin;
	}

	public abstract void reload();

	public void loadDefaults(ConfigurationSection section, boolean def) {
		this.enabled = section.getBoolean("enabled", def);
		this.displayName = section.getString("display-name");
		this.description = section.getString("description");
		this.category = section.getString("category", "None");
	}

	public Set<Class<? extends Event>> getEventClasses() {
		return this.eventTable.rowKeySet();
	}
	
	public <T extends Event> void addEventHandler(Class<T> type, Consumer<T> consumer) {
		addEventHandler(type, EventPriority.NORMAL, consumer);
	}

	public <T extends Event> void addEventHandler(Class<T> type, EventPriority priority, Consumer<T> consumer) {
		this.plugin.getEventManager().registerListener(type, priority);
		this.eventTable.put(type, priority, new EventWrapper<>(type, consumer));
	}

	public <T extends Event> Optional<EventWrapper<? extends Event>> getEventHandler(Class<T> type, EventPriority priority) {
		return Optional.ofNullable(this.eventTable.get(type, priority));
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

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public boolean isToggleable() {
		return false;
	}

	public boolean isDisabled(Player player) {
		return player.getPersistentDataContainer().has(new NamespacedKey(this.plugin, "toggle-" + this.name),
				PersistentDataType.BYTE);
	}
}
