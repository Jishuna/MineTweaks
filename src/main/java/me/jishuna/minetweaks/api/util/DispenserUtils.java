package me.jishuna.minetweaks.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DispenserUtils {
	public static void updateCauldron(JavaPlugin plugin, BlockDispenseEvent event, ItemStack item, Block target,
			Material material, Material toAdd) {
		target.setType(material);

		event.setCancelled(true);

		Bukkit.getScheduler().runTask(plugin, () -> {
			Container state = (Container) event.getBlock().getState();
			state.getSnapshotInventory().removeItem(item);
			state.getSnapshotInventory().addItem(new ItemStack(toAdd));
			state.update();
		});
	}

	public static void removeUsedItem(JavaPlugin plugin, Block block, ItemStack item) {
		item.setAmount(1);

		Bukkit.getScheduler().runTask(plugin, () -> {
			Container state = (Container) block.getState();
			state.getSnapshotInventory().removeItem(item);
			state.update();
		});
	}
}
