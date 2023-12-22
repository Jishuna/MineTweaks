package me.jishuna.minetweaks.tweak.farming;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.datastructure.WeightedRandom;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class SandBonemealTweak extends Tweak {

    @ConfigEntry("enable-for-players")
    @Comment("Lets players use bonemeal on sand")
    private boolean enablePlayer = true;

    @ConfigEntry("enable-for-dispensers")
    @Comment("Lets dispensers use bonemeal on sand")
    private boolean enableDispenser = true;

    @ConfigEntry("growth-weights")
    @Comment("Controls the possible plants grown as well as their weight")
    @Comment("Entries with a higher weight will be more common")
    private WeightedRandom<Material> growthChances = getDefaultChances();

    public SandBonemealTweak() {
        this.name = "sand-bonemealing";
        this.category = Category.FARMING;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
        registerEventConsumer(BlockDispenseEvent.class, this::onBlockDispense);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
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

    private void onBlockDispense(BlockDispenseEvent event) {
        // TODO
    }

    private static WeightedRandom<Material> getDefaultChances() {
        WeightedRandom<Material> random = new WeightedRandom<>();
        random.add(1, Material.CACTUS);
        random.add(9, Material.DEAD_BUSH);

        return random;
    }
}
