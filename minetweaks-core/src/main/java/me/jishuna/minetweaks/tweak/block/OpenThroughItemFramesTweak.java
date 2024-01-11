package me.jishuna.minetweaks.tweak.block;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class OpenThroughItemFramesTweak extends Tweak {

    public OpenThroughItemFramesTweak() {
        super("open-containers-through-item-frames", Category.BLOCK);
        this.description = List
                .of(ChatColor.GRAY + "Allows opening containers that item frames are attached to by right clicking them.",
                        ChatColor.GRAY + "Sneaking will allow interacting with the item frame as normal.");

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
