package me.jishuna.minetweaks.tweak.farming;

import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class SmallFlowerBonemealTweak extends Tweak {

    @ConfigEntry("flowers")
    @Comment("List of blocks that can be duplicated with bonemeal")
    private Set<Material> flowers = Set
            .of(Material.POPPY, Material.DANDELION, Material.RED_TULIP,
                    Material.WHITE_TULIP, Material.ORANGE_TULIP, Material.PINK_TULIP);

    public SmallFlowerBonemealTweak() {
        this.name = "small-flower-bonemealing";
        this.category = Category.FARMING;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || item == null || item.getType() != Material.BONE_MEAL || !this.flowers.contains(block.getType())) {
            return;
        }

        block.getDrops().forEach(drop -> block.getWorld().dropItem(block.getLocation().add(0.5, 0, 0.5), drop));

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
    }
}
