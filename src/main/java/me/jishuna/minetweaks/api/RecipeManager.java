package me.jishuna.minetweaks.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeManager {

	private static RecipeManager instance;

	private final List<Recipe> customRecipes = new ArrayList<>();

	public void addRecipe(JavaPlugin plugin, Recipe recipe) {
		if (recipe instanceof Keyed keyed) {
			Recipe current = Bukkit.getRecipe(keyed.getKey());

			if (current != null) {
				plugin.getLogger().warning("Ignoring duplicate recipe: " + keyed.getKey());
				return;
			}
		}

		try {
			Bukkit.addRecipe(recipe);
			this.customRecipes.add(recipe);
		} catch (IllegalStateException ex) {
			if (recipe instanceof Keyed keyed)
				plugin.getLogger().severe("Tried to add duplicate recipe: " + keyed.getKey());
		}
	}

	public void removeAllRecipes() {
		this.customRecipes.forEach(recipe -> {
			if (recipe instanceof Keyed keyed) {
				Bukkit.removeRecipe(keyed.getKey());
			}
		});
		this.customRecipes.clear();
	}

	public static RecipeManager getInstance() {
		if (instance == null)
			instance = new RecipeManager();
		return instance;
	}

	public static ShapedRecipe copyRecipe(JavaPlugin plugin, ShapedRecipe original, ItemStack result) {
		ShapedRecipe newRecipe = new ShapedRecipe(
				new NamespacedKey(plugin, result.getType().toString().toLowerCase() + "_extra"), result);
		newRecipe.shape(original.getShape());
		original.getChoiceMap().forEach(newRecipe::setIngredient);
		newRecipe.setGroup(original.getGroup());

		return newRecipe;
	}

}
