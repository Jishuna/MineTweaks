package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class PlayerListeners implements Listener {
    private final TweakRegistry registry;

    public PlayerListeners(TweakRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .build());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .build());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .build());
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .entity(event.getRightClicked())
                        .build());
    }

    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .entity(event.getEntity())
                        .build());
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        this.registry
                .processEvent(EventContext
                        .create(event)
                        .player(event.getPlayer())
                        .build());
    }
}
