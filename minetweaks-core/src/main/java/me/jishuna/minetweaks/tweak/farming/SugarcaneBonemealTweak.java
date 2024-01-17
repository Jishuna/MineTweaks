package me.jishuna.minetweaks.tweak.farming;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;
import me.jishuna.minetweaks.util.Utils;

@RegisterTweak
public class SugarcaneBonemealTweak extends Tweak {

    @ConfigEntry("enable-for-players")
    @Comment("Lets players grow sugarcane using bonemeal")
    private boolean enablePlayer = true;

    @ConfigEntry("enable-for-dispensers")
    @Comment("Lets dispensers grow sugarcane using bonemeal")
    private boolean enableDispenser = true;

    @ConfigEntry("max-height")
    @Comment("The maximum height a sugarcane can be grown to using bonemeal")
    private int maxHeight = 5;

    public SugarcaneBonemealTweak() {
        super("sugarcane-bonemealing", Category.FARMING);
        this.description = List
                .of(ChatColor.GRAY + "Allows players and dispensers to grow cactus using bonemeal.", "",
                        ChatColor.GRAY + "Enabled For Players: %player%",
                        ChatColor.GRAY + "Enabled For Dispensers: %dispenser%",
                        ChatColor.GRAY + "Max Growth Height: %max-height%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map
                .of("%player%", Utils.getDisplayString(this.enablePlayer),
                        "%dispenser%", Utils.getDisplayString(this.enableDispenser),
                        "%max-height%", ChatColor.GREEN.toString() + this.maxHeight);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!this.enablePlayer) {
            return;
        }

        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || item == null || item.getType() != Material.BONE_MEAL || block.getType() != Material.SUGAR_CANE) {
            return;
        }

        if (FarmingUtil.tryGrowTallPlant(block, this.maxHeight) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockDispense(BlockDispenseEvent event) {
        if (!this.enableDispenser || event.getBlock().getType() != Material.DISPENSER) {
            return;
        }

        ItemStack item = event.getItem();
        Directional directional = (Directional) event.getBlock().getBlockData();
        BlockFace face = directional.getFacing();
        Block block = event.getBlock().getRelative(face);

        if (item.getType() != Material.BONE_MEAL || block.getType() != Material.SUGAR_CANE) {
            return;
        }

        if (FarmingUtil.tryGrowTallPlant(block, this.maxHeight)) {
            ((Container) event.getBlock().getState()).getInventory().removeItem(new ItemStack(Material.BONE_MEAL));
        }
    }
}
