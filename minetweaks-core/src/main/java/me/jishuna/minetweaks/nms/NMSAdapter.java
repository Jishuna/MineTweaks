package me.jishuna.minetweaks.nms;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public interface NMSAdapter {
    public void createMovingBlock(Location location, BlockFace face, boolean extending, boolean source);

    public void updatePiston(Location location, boolean extending);
}
