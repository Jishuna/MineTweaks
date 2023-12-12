package me.jishuna.minetweaks.tweak.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class QuickShulkerBoxCreationTweak extends Tweak {

    public QuickShulkerBoxCreationTweak() {
        this.name = "quick-shulker-box-creation";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack hand = player.getEquipment().getItemInMainHand();
        ItemStack offhand = player.getEquipment().getItemInOffHand();

        if (hand.getType() != Material.SHULKER_SHELL || offhand.getType() != Material.SHULKER_SHELL) {
            return;
        }

        Block block = event.getClickedBlock();

        Container container = (Container) block.getState();
        Directional directional = (Directional) container.getBlockData();

        container.setBlockData(Material.SHULKER_BOX.createBlockData(data -> ((Directional) data).setFacing(directional.getFacing())));
        container.update(true);

        ((Container) block.getState()).getInventory().setContents(container.getSnapshotInventory().getContents());

        if (player.getGameMode() != GameMode.CREATIVE) {
            hand.setAmount(hand.getAmount() - 1);
            offhand.setAmount(offhand.getAmount() - 1);
        }
    }
}
