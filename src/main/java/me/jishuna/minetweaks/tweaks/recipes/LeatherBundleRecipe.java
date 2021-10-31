package me.jishuna.minetweaks.tweaks.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "leather_bundle")
public class LeatherBundleRecipe extends Tweak {

	public LeatherBundleRecipe(JavaPlugin plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "leather_bundle"),
					new ItemStack(Material.BUNDLE));
			recipe.shape("121", "202", "222");
			recipe.setIngredient('1', Material.STRING);
			recipe.setIngredient('2', Material.LEATHER);
			RecipeManager.getInstance().addRecipe(getOwningPlugin(), recipe);
		});

	}
}
