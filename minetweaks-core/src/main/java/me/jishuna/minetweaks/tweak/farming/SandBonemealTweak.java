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
import me.jishuna.jishlib.datastructure.WeightedRandom;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;
import me.jishuna.minetweaks.util.Utils;

@RegisterTweak
public class SandBonemealTweak extends Tweak {

    @ConfigEntry("enable-for-players")
    @Comment("Lets players use bonemeal on sand")
    private boolean enablePlayer = true;

    @ConfigEntry("enable-for-dispensers")
    @Comment("Lets dispensers use bonemeal on sand")
    private boolean enableDispenser = true;

    @ConfigEntry("growth-weights")
    @Comment("Controls the possible plants grown as well as their weights")
    @Comment("Entries with a higher weight will be more common")
    private WeightedRandom<Material> growthChances = getDefaultChances();

    public SandBonemealTweak() {
        super("sand-bonemealing", Category.FARMING);
        this.description = List
                .of(ChatColor.GRAY + "Allows players and dispensers to use bonemeal on sand to grow dead bushes and cactus.", "",
                        ChatColor.GRAY + "Enabled For Players: %player%",
                        ChatColor.GRAY + "Enabled For Dispensers: %dispenser%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map
                .of("%player%", Utils.getDisplayString(this.enablePlayer),
                        "%dispenser%", Utils.getDisplayString(this.enableDispenser));
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!this.enablePlayer) {
            return;
        }

        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || item == null || item.getType() != Material.BONE_MEAL) {
            return;
        }

        if (block.getType() == Material.SAND && !block.getRelative(BlockFace.UP).isLiquid()) {
            FarmingUtil.growPlants(block, this.growthChances);
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                item.setAmount(item.getAmount() - 1);
            }
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

        if (item.getType() != Material.BONE_MEAL || block.getType() != Material.SAND || block.getRelative(BlockFace.UP).isLiquid()) {
            return;
        }

        FarmingUtil.growPlants(block, this.growthChances);
        ((Container) event.getBlock().getState()).getInventory().removeItem(new ItemStack(Material.BONE_MEAL));
    }

    private static WeightedRandom<Material> getDefaultChances() {
        WeightedRandom<Material> random = new WeightedRandom<>();
        random.add(1, Material.CACTUS);
        random.add(9, Material.DEAD_BUSH);

        return random;
    }

}
