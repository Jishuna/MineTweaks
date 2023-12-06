package me.jishuna.minetweaks.tweak.block;

import java.util.Collections;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class OpenThroughItemFramesTweak extends Tweak {

    public OpenThroughItemFramesTweak() {
        this.name = "open-containers-through-item-frames";
        this.category = Category.BLOCK;
        this.listenedEvents = Collections.singletonList(PlayerInteractEntityEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        if (context.getPlayer().isSneaking() || (!(context.getEntity() instanceof ItemFrame frame))) {
            return;
        }

        Block attached = frame.getLocation().getBlock().getRelative(frame.getAttachedFace());

        if (!(attached.getState() instanceof Container container)) {
            return;
        }

        context.getPlayer().openInventory(container.getInventory());
        ((Cancellable) context.getEvent()).setCancelled(true);
    }
}
