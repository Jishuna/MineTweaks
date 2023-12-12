package me.jishuna.minetweaks.tweak.block;

import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class OpenThroughItemFramesTweak extends Tweak {

    public OpenThroughItemFramesTweak() {
        this.name = "open-containers-through-item-frames";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEntityEvent.class, this::onInteractEntity);
    }

    private void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() || (!(event.getRightClicked() instanceof ItemFrame frame))) {
            return;
        }

        Block attached = frame.getLocation().getBlock().getRelative(frame.getAttachedFace());

        if (!(attached.getState() instanceof Container container)) {
            return;
        }

        player.openInventory(container.getInventory());
        event.setCancelled(true);
    }
}
