package me.jishuna.minetweaks.nms;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

public interface NMSAdapter {
    public void activatePiston(Location location, BlockData data);
}
