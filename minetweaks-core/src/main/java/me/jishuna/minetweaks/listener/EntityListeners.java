package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import me.jishuna.minetweaks.Registries;

public class EntityListeners implements Listener {

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onToggleGliding(EntityToggleGlideEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Registries.TWEAK.processEvent(event);
    }
}
