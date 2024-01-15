package me.jishuna.minetweaks.inventory;

import org.bukkit.Tag;
import org.bukkit.block.Container;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.inventory.InventorySession;

public class ShulkerBoxInventory extends CustomInventory<Inventory> {
    private final ItemStack item;

    public ShulkerBoxInventory(ItemStack item) {
        super(getShulkerInventory(item));
        this.item = item;

        addClickConsumer((event, session) -> {
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null && Tag.SHULKER_BOXES.isTagged(clicked.getType())) {
                event.setCancelled(true);
            }
        });

        addCloseConsumer(this::onClose);
    }

    private static Inventory getShulkerInventory(ItemStack item) {
        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        return ((Container) meta.getBlockState()).getSnapshotInventory();
    }

    private void onClose(InventoryCloseEvent event, InventorySession session) {
        BlockStateMeta meta = (BlockStateMeta) this.item.getItemMeta();
        Container container = (Container) meta.getBlockState();
        container.getSnapshotInventory().setContents(getBukkitInventory().getContents());
        meta.setBlockState(container);

        this.item.setItemMeta(meta);
    }
}
