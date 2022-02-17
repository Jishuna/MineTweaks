package me.jishuna.minetweaks.tweaks.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("dispensers_from_droppers")
public class DispenserDropperRecipe extends Tweak {

	public DispenserDropperRecipe(MineTweaks plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
			
			if (!isEnabled())
				return;

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "dropper_dispenser"),
					new ItemStack(Material.DISPENSER));
			recipe.shape("012", "132", "012");
			recipe.setIngredient('1', Material.STICK);
			recipe.setIngredient('2', Material.STRING);
			recipe.setIngredient('3', Material.DROPPER);
			RecipeManager.getInstance().addRecipe(getPlugin(), recipe);
		});

	}
}
