package me.jishuna.minetweaks.nms.v1_17_R1;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;

public class NMSAdapter implements me.jishuna.minetweaks.nms.NMSAdapter {

	@Override
	public boolean canPlace(BlockData data, Location location) {
		return ((CraftBlockData) data).getState().canPlace(((CraftWorld) location.getWorld()).getHandle(),
				new BlockPosition(location.getX(), location.getY(), location.getZ()));
	}

	@Override
	public void attack(Player source, LivingEntity target) {
		EntityPlayer player = ((CraftPlayer) source).getHandle();

		player.attack(((CraftLivingEntity) target).getHandle());
		player.resetAttackCooldown();
	}

}
