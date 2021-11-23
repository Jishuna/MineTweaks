package me.jishuna.minetweaks.nms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface NMSAdapter {
	public default boolean canPlace(Material material, Location location) {
		return canPlace(material.createBlockData(), location);
	}

	public boolean canPlace(BlockData data, Location location);

	public void attack(Player source, LivingEntity target);
}
