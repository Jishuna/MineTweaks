package me.jishuna.minetweaks.tweak.farming;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.util.ItemUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class ImprovedHoesTweak extends Tweak implements ToggleableTweak {

    @ConfigEntry("hoe-radius")
    @Comment("The radius of blocks that will be tilled with each use. A value of 0 will result in no additional blocks being tilled.")
    private Map<Material, Integer> hoeRadius = Map
            .of(Material.WOODEN_HOE, 0, Material.STONE_HOE, 1,
                    Material.IRON_HOE, 1, Material.GOLDEN_HOE, 2,
                    Material.DIAMOND_HOE, 2, Material.NETHERITE_HOE, 3);

    public ImprovedHoesTweak() {
        super("improved-hoes", Category.FARMING);
        this.description = List.of(ChatColor.GRAY + "Allows hoes to till a radius of blocks at a time.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (!isEnabled(event.getPlayer()) || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || item == null || !Tag.DIRT.isTagged(block.getType())) {
            return;
        }

        Integer radius = this.hoeRadius.get(item.getType());
        if (radius == null) {
            return;
        }

        tillArea(block, item, radius, event.getPlayer(), event.getHand());
    }

    private void tillArea(Block source, ItemStack item, int radius, Player player, EquipmentSlot hand) {
        World world = source.getWorld();
        int startingY = source.getY() + 1;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (tillColumn(world, source.getX() + x, source.getZ() + z, startingY, player, item, hand)) {
                    return;
                }
            }
        }
    }

    private boolean tillColumn(World world, int x, int z, int startingY, Player player, ItemStack item, EquipmentSlot hand) {
        Block block;
        for (int y = 0; y <= 2; y++) {
            block = world.getBlockAt(x, startingY - y, z);
            if (Tag.DIRT.isTagged(block.getType()) && block.getRelative(BlockFace.UP).getType().isAir()) {
                block.setType(Material.FARMLAND);
                return ItemUtils.reduceDurability(player, item, hand);

            }
        }

        return false;
    }
}
