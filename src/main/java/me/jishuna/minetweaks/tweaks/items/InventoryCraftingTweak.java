package me.jishuna.minetweaks.tweaks.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("inventory_crafting_table")
public class InventoryCraftingTweak extends Tweak {
	public InventoryCraftingTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(InventoryClickEvent.class, EventPriority.HIGH, this::onClick);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onClick(InventoryClickEvent event) {
		if (event.isCancelled())
			return;
		
		if (event.getInventory().getType() != InventoryType.CRAFTING || event.getClick() != ClickType.SWAP_OFFHAND)
			return;

		ItemStack item = event.getCurrentItem();
		if (item == null || item.getType().isAir())
			return;

		if (item.getType() == Material.CRAFTING_TABLE) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(getPlugin(), () -> event.getWhoClicked().openWorkbench(null, true));
		}
	}
}
