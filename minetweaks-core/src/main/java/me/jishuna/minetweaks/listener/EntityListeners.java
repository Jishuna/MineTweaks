package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class EntityListeners implements Listener {
    private final TweakRegistry registry;

    public EntityListeners(TweakRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        this.registry.processEvent(event);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        this.registry.processEvent(event);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        this.registry.processEvent(event);
    }
}
