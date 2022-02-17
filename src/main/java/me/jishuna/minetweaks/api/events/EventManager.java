package me.jishuna.minetweaks.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import me.jishuna.commonlib.events.EventConsumer;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;

public class EventManager {
	private final MineTweaks plugin;

	private final Table<Class<? extends Event>, EventPriority, EventConsumer<? extends Event>> eventTable = HashBasedTable
			.create();

	public EventManager(MineTweaks plugin) {
		this.plugin = plugin;
	}

	public <T extends Event> boolean registerListener(Class<T> eventClass) {
		return registerListener(eventClass, EventPriority.NORMAL);
	}

	public <T extends Event> boolean registerListener(Class<T> eventClass, EventPriority priority) {
		if (isListenerRegistered(eventClass, priority))
			return false;

		EventConsumer<? extends Event> consumer = new EventConsumer<>(eventClass,
				event -> processEvent(event, eventClass, priority));
		consumer.register(this.plugin, priority);

		this.eventTable.put(eventClass, priority, consumer);
		return true;
	}

	public boolean isListenerRegistered(Class<? extends Event> eventClass, EventPriority priority) {
		return this.eventTable.contains(eventClass, priority);
	}

	public <T extends Event> void processEvent(T event, Class<T> eventClass, EventPriority priority) {
		for (Tweak tweak : this.plugin.getTweakManager().getTweaksForEvent(eventClass)) {
			if (tweak.isEnabled()) {
				tweak.getEventHandler(eventClass, priority).ifPresent(wrapper -> wrapper.consume(event));
			}
		}
	}
}
