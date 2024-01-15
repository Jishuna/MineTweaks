package me.jishuna.minetweaks.tweak.farming;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class CactusBonemealTweak extends Tweak {

    @ConfigEntry("max-height")
    @Comment("The maximum height a cactus can be grown to using bonemeal")
    private int maxHeight = 5;

    public CactusBonemealTweak() {
        super("cactus-bonemealing", Category.FARMING);
        this.description = List
                .of(ChatColor.GRAY + "Allows players to grow cactus using bonemeal.", "",
                        ChatColor.GRAY + "Max Growth Height: %max-height%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%max-height%", ChatColor.GREEN.toString() + this.maxHeight);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || item == null || item.getType() != Material.BONE_MEAL || block.getType() != Material.CACTUS) {
            return;
        }

        if (FarmingUtil.tryGrowTallPlant(block, this.maxHeight) && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
    }
}
