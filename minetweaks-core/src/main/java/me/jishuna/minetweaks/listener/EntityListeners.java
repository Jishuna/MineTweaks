package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class EntityListeners implements Listener {
    private final TweakRegistry registry;

    public EntityListeners(TweakRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        this.registry
                .process(EventContext
                        .create(event)
                        .entity(event.getEntity())
                        .build());
    }
}
