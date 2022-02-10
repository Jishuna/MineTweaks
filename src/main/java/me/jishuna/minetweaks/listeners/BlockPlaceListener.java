package me.jishuna.minetweaks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.minetweaks.api.util.CustomItemUtils;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		ItemStack item = event.getItemInHand();
		if (item != null && item.hasItemMeta() && !CustomItemUtils.getCustomItemType(item).isBlank())
			event.setCancelled(true);
	}
}
