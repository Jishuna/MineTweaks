package me.jishuna.minetweaks.nms.v1_18_R2;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class NMSAdapter implements me.jishuna.minetweaks.nms.NMSAdapter {

	@Override
	public boolean canPlace(BlockData data, Location location) {
		return location.getBlock().canPlace(data);
	}

	@Override
	public void attack(Player source, LivingEntity target) {
		source.attack(target);
	}

}
