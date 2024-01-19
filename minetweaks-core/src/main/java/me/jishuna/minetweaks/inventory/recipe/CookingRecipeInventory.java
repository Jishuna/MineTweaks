package me.jishuna.minetweaks.inventory.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmokingRecipe;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.inventory.InventoryConstants;

public class CookingRecipeInventory extends RecipeInventory {
    public CookingRecipeInventory(CookingRecipe<?> recipe) {
        super(Bukkit.createInventory(null, 54, MessageAPI.get("gui.recipe.name")), recipe.getResult());

        cancelAllClicks();
        populate(recipe);
    }

    private void populate(CookingRecipe<?> recipe) {
        placeRecipe(recipe);

        setButton(45, InventoryConstants.PREVIOUS_MENU, (event, session) -> session.openPrevious());
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.ACCENT_FILLER, 14, 15, 16, 23, 25, 32, 33, 34);
        fillEmpty(InventoryConstants.FILLER);
    }

    private void placeRecipe(CookingRecipe<?> recipe) {
        setItem(11, recipe.getInput());
        setItem(20, getRecipeIcon(recipe));
        setItem(29, new ItemStack(Material.COAL));

        setItem(24, recipe.getResult());
    }

    private ItemStack getRecipeIcon(CookingRecipe<?> recipe) {
        if (recipe instanceof SmokingRecipe) {
            return ItemBuilder.create(Material.SMOKER).name(MessageAPI.get("recipe.smoker")).build();
        }

        if (recipe instanceof BlastingRecipe) {
            return ItemBuilder.create(Material.BLAST_FURNACE).name(MessageAPI.get("recipe.blast-furnace")).build();
        }

        if (recipe instanceof CampfireRecipe) {
            return ItemBuilder.create(Material.CAMPFIRE).name(MessageAPI.get("recipe.campfire")).build();
        }

        return ItemBuilder.create(Material.FURNACE).name(MessageAPI.get("recipe.furnace")).build();
    }
}
