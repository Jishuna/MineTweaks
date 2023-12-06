package me.jishuna.minetweaks.tweak.block;

import java.util.Collections;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class QuickShulkerBoxCreationTweak extends Tweak {

    public QuickShulkerBoxCreationTweak() {
        this.name = "quick-shulker-box-creation";
        this.category = Category.BLOCK;
        this.listenedEvents = Collections.singletonList(PlayerInteractEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        PlayerInteractEvent event = (PlayerInteractEvent) context.getEvent();
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }

        ItemStack hand = context.getPlayer().getEquipment().getItemInMainHand();
        ItemStack offhand = context.getPlayer().getEquipment().getItemInOffHand();

        if (hand.getType() != Material.SHULKER_SHELL || offhand.getType() != Material.SHULKER_SHELL) {
            return;
        }

        Block block = event.getClickedBlock();

        Container container = (Container) block.getState();
        Directional directional = (Directional) container.getBlockData();

        container.setBlockData(Material.SHULKER_BOX.createBlockData(data -> ((Directional) data).setFacing(directional.getFacing())));
        container.update(true);

        ((Container) block.getState()).getInventory().setContents(container.getSnapshotInventory().getContents());

        if (context.getPlayer().getGameMode() != GameMode.CREATIVE) {
            hand.setAmount(hand.getAmount() - 1);
            offhand.setAmount(offhand.getAmount() - 1);
        }
    }
}
