package me.jishuna.minetweaks.api.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.minetweaks.api.PluginKeys;

public class CustomItemUtils {
	
	public static String getCustomItemType(ItemStack item) {
		return item.getItemMeta().getPersistentDataContainer()
				.getOrDefault(PluginKeys.ITEM_TYPE.getKey(), PersistentDataType.STRING, "");
	}

}
