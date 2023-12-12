package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import me.jishuna.minetweaks.tweak.TweakRegistry;

public class MiscListeners implements Listener {
    private final TweakRegistry registry;

    public MiscListeners(TweakRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        this.registry.processEvent(event);
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        this.registry.processEvent(event);
    }
}
