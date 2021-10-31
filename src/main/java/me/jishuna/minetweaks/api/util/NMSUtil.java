package me.jishuna.minetweaks.api.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.data.CraftBlockData;

import net.minecraft.core.BlockPosition;

public class NMSUtil {

	public static boolean canPlace(Material material, Location location) {
		return ((CraftBlockData) material.createBlockData()).getState().canPlace(
				((CraftWorld) location.getWorld()).getHandle(),
				new BlockPosition(location.getX(), location.getY(), location.getZ()));
	}

	public static boolean canPlace(BlockData data, Location location) {
		return ((CraftBlockData) data).getState().canPlace(((CraftWorld) location.getWorld()).getHandle(),
				new BlockPosition(location.getX(), location.getY(), location.getZ()));
	}
}