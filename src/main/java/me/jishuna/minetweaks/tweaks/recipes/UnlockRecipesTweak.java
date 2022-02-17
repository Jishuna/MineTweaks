package me.jishuna.minetweaks.tweaks.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("unlock_on_join")
public class UnlockRecipesTweak extends Tweak {

	public UnlockRecipesTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerJoinEvent.class, this::onJoin);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Recipes/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});

	}

	private void onJoin(PlayerJoinEvent event) {
		Iterator<Recipe> iterator = Bukkit.recipeIterator();
		List<NamespacedKey> recipes = new ArrayList<>();

		while (iterator.hasNext()) {
			Recipe recipe = iterator.next();
			if (recipe instanceof Keyed keyed) {
				recipes.add(keyed.getKey());
			}
		} 

		event.getPlayer().discoverRecipes(recipes);
	}
}
