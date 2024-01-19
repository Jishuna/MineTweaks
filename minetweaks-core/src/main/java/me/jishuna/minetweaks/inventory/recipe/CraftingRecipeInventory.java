package me.jishuna.minetweaks.inventory.recipe;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.inventory.InventoryConstants;

public class CraftingRecipeInventory extends RecipeInventory {
    private static final List<Integer> INGREDIENT_SLOTS = List.of(10, 11, 12, 19, 20, 21, 28, 29, 30);

    public CraftingRecipeInventory(Recipe recipe) {
        super(Bukkit.createInventory(null, 54, MessageAPI.get("gui.recipe.name")), recipe.getResult());

        cancelAllClicks();
        populate(recipe);
    }

    private void populate(Recipe recipe) {
        placeRecipe(recipe);

        setButton(45, InventoryConstants.PREVIOUS_MENU, (event, session) -> session.openPrevious());
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.ACCENT_FILLER, 14, 15, 16, 23, 25, 32, 33, 34);

        for (int i = 0; i < 54; i++) {
            if (INGREDIENT_SLOTS.contains(i) || hasItem(i)) {
                continue;
            }

            setItem(i, InventoryConstants.FILLER);
        }
    }

    private void placeRecipe(Recipe recipe) {
        if (recipe instanceof ShapedRecipe shaped) {
            setItem(38, ItemBuilder.create(Material.CRAFTING_TABLE).name(MessageAPI.get("recipe.shaped")).build());

            int row = 0;
            int col = 0;
            for (String line : shaped.getShape()) {
                for (char chr : line.toCharArray()) {
                    int index = row * 3 + col;
                    setItem(INGREDIENT_SLOTS.get(index), shaped.getIngredientMap().get(chr));
                    col++;
                }
                col = 0;
                row++;
            }
        } else if (recipe instanceof ShapelessRecipe shapeless) {
            setItem(38, ItemBuilder.create(Material.CRAFTING_TABLE).name(MessageAPI.get("recipe.shapeless")).build());

            int index = 0;
            for (ItemStack item : shapeless.getIngredientList()) {
                setItem(INGREDIENT_SLOTS.get(index++), item);
            }
        }

        setItem(24, recipe.getResult());
    }
}
