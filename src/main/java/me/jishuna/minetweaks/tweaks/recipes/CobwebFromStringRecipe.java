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

@RegisterTweak(name = "cobweb_from_string")
public class CobwebFromStringRecipe extends Tweak {

	public CobwebFromStringRecipe(JavaPlugin plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			if (!isEnabled())
				return;

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "cobweb_string"),
					new ItemStack(Material.COBWEB));
			recipe.shape("101", "010", "101");
			recipe.setIngredient('1', Material.STRING);
			RecipeManager.getInstance().addRecipe(getOwningPlugin(), recipe);
		});

	}
}
