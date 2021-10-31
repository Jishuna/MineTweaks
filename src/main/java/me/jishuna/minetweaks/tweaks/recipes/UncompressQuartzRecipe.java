package me.jishuna.minetweaks.tweaks.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "uncompress_quartz")
public class UncompressQuartzRecipe extends Tweak {

	public UncompressQuartzRecipe(JavaPlugin plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getOwningPlugin(), "uncompress_quartz"),
					new ItemStack(Material.QUARTZ, 4));
			recipe.addIngredient(Material.QUARTZ_BLOCK);
			RecipeManager.getInstance().addRecipe(getOwningPlugin(), recipe);
		});

	}
}
