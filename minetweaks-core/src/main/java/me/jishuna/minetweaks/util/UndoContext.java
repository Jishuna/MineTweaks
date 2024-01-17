package me.jishuna.minetweaks.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.jishuna.jishlib.util.InventoryUtils;

public class UndoContext {
    private final List<Location> locations = new ArrayList<>();
    private final UUID uuid;

    public UndoContext(Player player) {
        this.uuid = player.getUniqueId();
    }

    public void addBlock(Block block) {
        addLocation(block.getLocation());
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void undo() {
        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        for (Location location : this.locations) {
            Block block = location.getBlock();
            if (block.isEmpty()) {
                continue;
            }

            InventoryUtils.addOrDropItem(inventory, player::getLocation, new ItemStack(block.getBlockData().getPlacementMaterial()));
            block.setType(Material.AIR);
        }
    }
}
