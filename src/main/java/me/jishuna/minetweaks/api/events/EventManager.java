package me.jishuna.minetweaks.api.events;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.jishuna.commonlib.events.EventConsumer;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;

public class EventManager {
	private final MineTweaks plugin;

	private Map<Class<? extends Event>, EventConsumer<? extends Event>> listenerMap = new HashMap<>();

	public EventManager(MineTweaks plugin) {
		this.plugin = plugin;
		registerBaseEvents();
	}

	public <T extends Event> boolean registerListener(Class<T> eventClass, Consumer<T> handler) {
		return registerListener(eventClass, handler, EventPriority.NORMAL);
	}

	public <T extends Event> boolean registerListener(Class<T> eventClass, Consumer<T> handler,
			EventPriority priority) {
		if (isListenerRegistered(eventClass))
			return false;

		EventConsumer<? extends Event> consumer = new EventConsumer<>(eventClass, handler);
		consumer.register(this.plugin, priority);

		this.listenerMap.put(eventClass, consumer);
		return true;
	}

	public boolean isListenerRegistered(Class<? extends Event> eventClass) {
		return this.listenerMap.containsKey(eventClass);
	}

	public <T extends Event> void processEvent(T event, Class<T> eventClass) {
		for (Tweak tweak : this.plugin.getTweakManager().getTweaksForEvent(eventClass)) {
			if (tweak.isEnabled()) {
				tweak.getEventHandlers(eventClass).forEach(consumer -> consumer.consume(event));
			}
		}
	}

	private void registerBaseEvents() {
		registerListener(PlayerInteractAtEntityEvent.class, event -> processEvent(event, PlayerInteractAtEntityEvent.class), EventPriority.HIGHEST);
		registerListener(PlayerInteractEntityEvent.class, event -> processEvent(event, PlayerInteractEntityEvent.class), EventPriority.HIGHEST);
		registerListener(PlayerInteractEvent.class, event -> processEvent(event, PlayerInteractEvent.class), EventPriority.HIGHEST);
		registerListener(EntityInteractEvent.class, event -> processEvent(event, EntityInteractEvent.class), EventPriority.HIGHEST);
		registerListener(BlockDispenseEvent.class, event -> processEvent(event, BlockDispenseEvent.class), EventPriority.HIGHEST);
		registerListener(EntityDamageEvent.class, event -> processEvent(event, EntityDamageEvent.class), EventPriority.HIGHEST);
		registerListener(CreatureSpawnEvent.class, event -> processEvent(event, CreatureSpawnEvent.class));
		registerListener(EntityCombustEvent.class, event -> processEvent(event, EntityCombustEvent.class));
		registerListener(PlayerJoinEvent.class, event -> processEvent(event, PlayerJoinEvent.class));
		registerListener(EntityExplodeEvent.class, event -> processEvent(event, EntityExplodeEvent.class));
		registerListener(EntityChangeBlockEvent.class, event -> processEvent(event, EntityChangeBlockEvent.class));
		registerListener(PotionSplashEvent.class, event -> processEvent(event, PotionSplashEvent.class));
		registerListener(PrepareAnvilEvent.class, event -> processEvent(event, PrepareAnvilEvent.class));
		registerListener(NotePlayEvent.class, event -> processEvent(event, NotePlayEvent.class));
	}
}
