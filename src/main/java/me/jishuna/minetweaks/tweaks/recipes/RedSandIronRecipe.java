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

@RegisterTweak(name = "red_sand_iron")
public class RedSandIronRecipe extends Tweak {

	public RedSandIronRecipe(JavaPlugin plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "redden_sand"),
					new ItemStack(Material.RED_SAND, 8));
			recipe.setGroup("redden_sand");
			recipe.shape("111", "101", "111");
			recipe.setIngredient('1', Material.SAND);
			recipe.setIngredient('0', Material.IRON_NUGGET);
			RecipeManager.getInstance().addRecipe(getOwningPlugin(), recipe);
		});

	}
}
