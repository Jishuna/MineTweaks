package me.jishuna.minetweaks.tweak.block;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class SilktouchSpawnersTweak extends Tweak {

    public SilktouchSpawnersTweak() {
        super("silk-touch-spawners", Category.BLOCK);
        this.description = List.of(ChatColor.GRAY + "Allows players to pick up spawners when using a silk touch pickaxe.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getEquipment().getItemInMainHand();
        if (block.getType() != Material.SPAWNER || !Tag.ITEMS_PICKAXES.isTagged(tool.getType()) || tool.getEnchantmentLevel(Enchantment.SILK_TOUCH) < 1) {
            return;
        }

        ItemStack item = ItemBuilder
                .create(Material.SPAWNER)
                .modify(BlockStateMeta.class, meta -> meta.setBlockState(block.getState()))
                .build();

        block.getWorld().dropItemNaturally(block.getLocation(), item);
        event.setDropItems(false);
        event.setExpToDrop(0);
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.getType() != Material.SPAWNER) {
            return;
        }

        BlockState held = ((BlockStateMeta) item.getItemMeta()).getBlockState();
        JishLib.run(() -> {
            BlockState placed = event.getBlock().getState();

            if (placed instanceof CreatureSpawner placedSpawner && held instanceof CreatureSpawner heldSpawner) {
                EntitySnapshot snapshot = heldSpawner.getSpawnedEntity();
                if (snapshot != null) {
                    placedSpawner.setSpawnedEntity(snapshot);
                }

                placedSpawner.setPotentialSpawns(heldSpawner.getPotentialSpawns());

                placedSpawner.setMinSpawnDelay(heldSpawner.getMinSpawnDelay());
                placedSpawner.setMaxSpawnDelay(heldSpawner.getMaxSpawnDelay());
                placedSpawner.setDelay(placedSpawner.getMaxSpawnDelay());

                placedSpawner.setRequiredPlayerRange(heldSpawner.getRequiredPlayerRange());
                placedSpawner.setSpawnCount(heldSpawner.getSpawnCount());
                placedSpawner.setSpawnRange(heldSpawner.getSpawnRange());

                placedSpawner.update();
            }
        });
    }
}
