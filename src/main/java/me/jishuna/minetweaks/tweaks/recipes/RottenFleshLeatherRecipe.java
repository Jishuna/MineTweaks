package me.jishuna.minetweaks.tweaks.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("rotten_flesh_leather")
public class RottenFleshLeatherRecipe extends Tweak {

	public RottenFleshLeatherRecipe(MineTweaks plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, false);
			
			if (!isEnabled())
				return;

			FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(getPlugin(), "rotten_flesh_leather"),
					new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 0.1f,
					config.getInt("rotten-flesh-cook-time", 200));
			RecipeManager.getInstance().addRecipe(getPlugin(), recipe);
		});

	}
}
