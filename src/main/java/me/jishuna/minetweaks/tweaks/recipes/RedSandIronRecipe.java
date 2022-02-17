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

@RegisterTweak("red_sand_iron")
public class RedSandIronRecipe extends Tweak {

	public RedSandIronRecipe(MineTweaks plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
			
			if (!isEnabled())
				return;

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "redden_sand"),
					new ItemStack(Material.RED_SAND, 8));
			recipe.setGroup("redden_sand");
			recipe.shape("111", "101", "111");
			recipe.setIngredient('1', Material.SAND);
			recipe.setIngredient('0', Material.IRON_NUGGET);
			RecipeManager.getInstance().addRecipe(getPlugin(), recipe);
		});

	}
}
