package me.jishuna.minetweaks.tweak.crafting;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.minetweaks.inventory.recipe.RecipeInventory;
import me.jishuna.minetweaks.inventory.recipe.RecipeListInventory;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class CustomCraftingRecipesTweak extends Tweak {
    private final List<RecipeInventory> recipeInventories = new ArrayList<>();

    @ConfigEntry("recipes")
    @Comment("A list of recipes that will be added when this tweak is enabled")
    private List<Recipe> recipes = getDefaultRecipes();

    private RecipeListInventory inventory;

    public CustomCraftingRecipesTweak() {
        super("custom-crafting-recipes", Category.CRAFTING);
        this.description = List
                .of(ChatColor.GRAY + "Adds various custom crafting recipes to the game.", "",
                        ChatColor.GREEN + "Click to view recipes.");
    }

    @Override
    public void reload() {
        super.reload();
        this.recipeInventories.clear();

        this.recipes.forEach(recipe -> {
            Bukkit.removeRecipe(((Keyed) recipe).getKey());
            if (this.enabled) {
                Bukkit.addRecipe(recipe);
                this.recipeInventories.add(RecipeInventory.create(recipe));
            }
        });

        this.inventory = new RecipeListInventory(this.recipeInventories);
    }

    @Override
    public void onMenuClick(InventoryClickEvent event, InventorySession session) {
        if (this.inventory != null) {
            session.changeTo(this.inventory, true);
        }
    }

    private static List<Recipe> getDefaultRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        ShapedRecipe stringCobweb = new ShapedRecipe(NamespacedKey.fromString("minetweaks:string_cobweb"), new ItemStack(Material.COBWEB));
        stringCobweb.shape("1 1", " 1 ", "1 1");
        stringCobweb.setIngredient('1', Material.STRING);
        recipes.add(stringCobweb);

        ShapelessRecipe cobwebString = new ShapelessRecipe(NamespacedKey.fromString("minetweaks:cobweb_string"), new ItemStack(Material.STRING, 5));
        cobwebString.addIngredient(Material.COBWEB);
        recipes.add(cobwebString);

        ShapelessRecipe quartz = new ShapelessRecipe(NamespacedKey.fromString("minetweaks:uncompress_quartz"), new ItemStack(Material.QUARTZ, 4));
        quartz.addIngredient(Material.QUARTZ_BLOCK);
        recipes.add(quartz);

        ShapedRecipe dispenserDropper = new ShapedRecipe(NamespacedKey.fromString("minetweaks:dispenser_dropper"), new ItemStack(Material.DISPENSER));
        dispenserDropper.shape("012", "132", "012");
        dispenserDropper.setIngredient('1', Material.STICK);
        dispenserDropper.setIngredient('2', Material.STRING);
        dispenserDropper.setIngredient('3', Material.DROPPER);
        recipes.add(dispenserDropper);

        recipes.add(new FurnaceRecipe(NamespacedKey.fromString("minetweaks:rotten_flesh_leather"), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 1, 200));

        return recipes;
    }
}
