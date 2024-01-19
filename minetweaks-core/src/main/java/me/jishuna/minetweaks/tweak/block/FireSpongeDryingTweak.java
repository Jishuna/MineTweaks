package me.jishuna.minetweaks.tweak.block;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.util.ItemUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class FireSpongeDryingTweak extends Tweak {

    public FireSpongeDryingTweak() {
        super("fire-sponge-drying", Category.BLOCK);
        this.description = List
                .of(ChatColor.GRAY + "Allows wet sponge to be dried when right clicked with a flint and steel.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();

        if (block.getType() != Material.WET_SPONGE || item == null || item.getType() != Material.FLINT_AND_STEEL) {
            return;
        }

        block.setType(Material.SPONGE);
        event.setUseItemInHand(Result.DENY);

        ItemUtils.reduceDurability(event.getPlayer(), item, event.getHand());
    }
}
