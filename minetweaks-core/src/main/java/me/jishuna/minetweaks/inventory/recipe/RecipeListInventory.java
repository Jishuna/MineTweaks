package me.jishuna.minetweaks.inventory.recipe;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.inventory.InventoryConstants;

public class RecipeListInventory extends PagedCustomInventory<RecipeInventory, Inventory> {

    public RecipeListInventory(Collection<RecipeInventory> recipes) {
        super(Bukkit.createInventory(null, 54, MessageAPI.get("gui.recipes.name")), recipes, 45);

        cancelAllClicks();

        populate();
        refreshOptions();
    }

    private void populate() {
        setButton(45, InventoryConstants.PREVIOUS_MENU, (event, session) -> session.openPrevious());
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.FILLER, 45, 46, 47, 48, 50, 51, 52, 53);
    }

    @Override
    protected ItemStack asItemStack(RecipeInventory inventory) {
        return inventory.getResult();
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, RecipeInventory inventory) {
        session.changeTo(inventory, true);
    }
}
