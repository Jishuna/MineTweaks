package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import me.jishuna.minetweaks.Registries;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        Registries.TWEAK.processEvent(event);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Registries.TWEAK.processEvent(event);
    }
}
