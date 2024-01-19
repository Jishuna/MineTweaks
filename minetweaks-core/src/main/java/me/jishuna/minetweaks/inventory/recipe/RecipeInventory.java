package me.jishuna.minetweaks.inventory.recipe;

import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import me.jishuna.jishlib.inventory.CustomInventory;

public abstract class RecipeInventory extends CustomInventory<Inventory> {
    private final ItemStack result;

    protected RecipeInventory(Inventory inventory, ItemStack result) {
        super(inventory);
        this.result = result;

        cancelAllClicks();
    }

    public static RecipeInventory create(Recipe recipe) {
        if (recipe instanceof CookingRecipe<?> cooking) {
            return new CookingRecipeInventory(cooking);
        }

        return new CraftingRecipeInventory(recipe);
    }

    public ItemStack getResult() {
        return this.result;
    }
}
