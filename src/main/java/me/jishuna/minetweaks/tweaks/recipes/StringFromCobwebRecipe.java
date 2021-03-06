package me.jishuna.minetweaks.tweaks.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("string_from_cobweb")
public class StringFromCobwebRecipe extends Tweak {

	public StringFromCobwebRecipe(MineTweaks plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
			
			if (!isEnabled())
				return;

			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(getPlugin(), "string_cobweb"),
					new ItemStack(Material.STRING, 5));
			recipe.addIngredient(Material.COBWEB);
			RecipeManager.getInstance().addRecipe(getPlugin(), recipe);
		});

	}
}
