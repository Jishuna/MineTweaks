package me.jishuna.minetweaks.tweak.crafting;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class CustomCraftingRecipesTweak extends Tweak {

    @ConfigEntry("recipes")
    @Comment("A list of recipes that will be added when this tweak is enabled")
    private List<Recipe> recipes = getDefaultRecipes();

    public CustomCraftingRecipesTweak() {
        this.name = "custom-crafting-recipes";
        this.category = Category.CRAFTING;
    }

    @Override
    public void reload() {
        super.reload();

        this.recipes.forEach(recipe -> {
            Bukkit.removeRecipe(((Keyed) recipe).getKey());
            if (this.enabled) {
                Bukkit.addRecipe(recipe);
            }
        });
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

        recipes.add(new FurnaceRecipe(NamespacedKey.fromString("minetweaks:rotten_flesh_leather"), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 1, 200));

        return recipes;
    }
}
