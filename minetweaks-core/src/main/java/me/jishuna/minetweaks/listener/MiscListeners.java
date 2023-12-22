package me.jishuna.minetweaks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import me.jishuna.minetweaks.Registries;

public class MiscListeners implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        Registries.TWEAKS.processEvent(event);
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        Registries.TWEAKS.processEvent(event);
    }

    @EventHandler
    public void onBlockDropItems(BlockDropItemEvent event) {
        Registries.TWEAKS.processEvent(event);
    }
}
