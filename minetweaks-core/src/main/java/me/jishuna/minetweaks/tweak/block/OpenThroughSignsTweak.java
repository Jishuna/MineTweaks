package me.jishuna.minetweaks.tweak.block;

import java.util.Collections;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class OpenThroughSignsTweak extends Tweak {

    public OpenThroughSignsTweak() {
        this.name = "open-containers-through-signs";
        this.category = Category.BLOCK;
        this.listenedEvents = Collections.singletonList(PlayerInteractEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        PlayerInteractEvent event = (PlayerInteractEvent) context.getEvent();
        Block block = event.getClickedBlock();
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                context.getPlayer().isSneaking() || !Tag.WALL_SIGNS.isTagged(block.getType())) {
            return;
        }

        Directional directional = (Directional) block.getBlockData();
        Block attached = block.getRelative(directional.getFacing().getOppositeFace());

        if (!(attached.getState() instanceof Container container)) {
            return;
        }

        context.getPlayer().openInventory(container.getInventory());
        event.setCancelled(true);
    }
}
