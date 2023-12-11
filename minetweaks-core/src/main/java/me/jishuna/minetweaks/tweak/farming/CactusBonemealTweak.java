package me.jishuna.minetweaks.tweak.farming;

import java.util.Collections;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class CactusBonemealTweak extends Tweak {

    @ConfigEntry("max-height")
    @Comment("The maximum height a cactus can be grown to using bonemeal")
    private final int maxHeight = 5;

    public CactusBonemealTweak() {
        this.name = "cactus-bonemealing";
        this.category = Category.FARMING;
        this.listenedEvents = Collections.singletonList(PlayerInteractEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        if (context.getEvent() instanceof PlayerInteractEvent event) {
            handlePlayer(event);
        }
    }

    private void handlePlayer(PlayerInteractEvent event) {
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
