package me.jishuna.minetweaks.inventory;

import java.text.MessageFormat;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;

public class MobHeadInventory extends PagedCustomInventory<ItemStack, Inventory> {

    public MobHeadInventory(Collection<ItemStack> items, String name) {
        super(Bukkit.createInventory(null, 54, MessageFormat.format(name, items.size())), items, 45);

        cancelAllClicks();

        populate();
        refreshOptions();
    }

    private void populate() {
        setButton(45, InventoryConstants.PREVIOUS_MENU, (event, session) -> session.openPrevious());
        setButton(52, InventoryConstants.PREVIOUS_PAGE, (event, session) -> changePage(-1));
        setButton(53, InventoryConstants.NEXT_PAGE, (event, session) -> changePage(1));
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.FILLER, 46, 47, 48, 50, 51, 52);
    }

    @Override
    protected ItemStack asItemStack(ItemStack item) {
        return item;
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, ItemStack item) {
        // Do nothing
    }

}
