package me.jishuna.minetweaks.tweak.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class StickyPistonConversionTweak extends Tweak {

    public StickyPistonConversionTweak() {
        this.name = "sticky-piston-conversion";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null) {
            return;
        }

        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();

        if (block.getType() == Material.PISTON && item.getType() == Material.SLIME_BALL) {
            Directional oldData = (Directional) block.getBlockData();

            block.setType(Material.STICKY_PISTON);
            Directional data = (Directional) block.getBlockData();
            data.setFacing(oldData.getFacing());
            block.setBlockData(data);

            if (player.getGameMode() != GameMode.CREATIVE) {
                item.setAmount(item.getAmount() - 1);
            }

            player.playSound(block.getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1f, 1f);
        }
    }
}
