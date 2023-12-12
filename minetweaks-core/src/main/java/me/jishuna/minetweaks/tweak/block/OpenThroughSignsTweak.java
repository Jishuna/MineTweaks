package me.jishuna.minetweaks.tweak.block;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class OpenThroughSignsTweak extends Tweak {

    public OpenThroughSignsTweak() {
        this.name = "open-containers-through-signs";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                player.isSneaking() || !Tag.WALL_SIGNS.isTagged(block.getType())) {
            return;
        }

        Directional directional = (Directional) block.getBlockData();
        Block attached = block.getRelative(directional.getFacing().getOppositeFace());

        if (!(attached.getState() instanceof Container container)) {
            return;
        }

        player.openInventory(container.getInventory());
        event.setCancelled(true);
    }
}
