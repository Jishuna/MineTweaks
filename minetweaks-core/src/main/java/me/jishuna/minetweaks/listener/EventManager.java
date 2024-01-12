package me.jishuna.minetweaks.listener;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.Tweak;

public class EventManager implements Listener {
    private final Set<Class<? extends Event>> registered = new HashSet<>();
    private final Plugin plugin;

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents(Tweak tweak) {
        PluginManager manager = Bukkit.getPluginManager();

        for (Class<? extends Event> clazz : tweak.getEventClasses()) {
            if (this.registered.contains(clazz)) {
                continue;
            }

            manager.registerEvent(clazz, this, EventPriority.NORMAL, (listener, event) -> {
                if (clazz.isInstance(event)) {
                    Registries.TWEAK.processEvent(event);
                }
            }, this.plugin, true);
            this.registered.add(clazz);
        }
    }
}
