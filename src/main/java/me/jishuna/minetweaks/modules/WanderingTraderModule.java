package me.jishuna.minetweaks.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.util.internal.ThreadLocalRandom;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class WanderingTraderModule extends TweakModule {

	private List<MerchantRecipe> recipes;
	private String message;

	public WanderingTraderModule(JavaPlugin plugin) {
		super(plugin, "wandering_trader");
		
		addSubModule("announce-spawn");

		addEventHandler(CreatureSpawnEvent.class, this::onSpawn);
	}

	@Override
	public void reload() {
		super.reload();

		if (!isEnabled())
			return;

		this.recipes = new ArrayList<>();
		this.message = ChatColor.translateAlternateColorCodes('&', getString("announce-message", "Placeholder"));

		for (String entry : getStringList("custom-trades")) {
			String[] data = entry.split(",");

			if (data.length < 4)
				continue;

			Material buy = Material.getMaterial(data[0].toUpperCase());
			Material sell = Material.getMaterial(data[2].toUpperCase());

			if (buy == null || sell == null)
				continue;

			if (!StringUtils.isNumeric(data[1]) || !StringUtils.isNumeric(data[3]))
				continue;

			int buyCount = Integer.parseInt(data[1]);
			int sellCount = Integer.parseInt(data[3]);

			MerchantRecipe recipe = new MerchantRecipe(new ItemStack(sell, sellCount), 10);
			recipe.addIngredient(new ItemStack(buy, buyCount));

			recipes.add(recipe);
		}
	}

	private void onSpawn(CreatureSpawnEvent event) {
		if (isEnabled() && event.getEntity()instanceof WanderingTrader trader) {
			List<MerchantRecipe> newRecipes = new ArrayList<>();
			Random random = ThreadLocalRandom.current();

			int min = getInt("min-trades", 5);
			int max = getInt("max-trades", 7);
			int count = random.nextInt(max - min) + min;

			for (int i = 0; i < count; i++) {
				MerchantRecipe recipe = this.recipes.get(random.nextInt(this.recipes.size()));

				if (!newRecipes.contains(recipe)) {
					newRecipes.add(recipe);
				}
			}

			trader.setRecipes(newRecipes);

			if (getBoolean("announce-spawn", true)) {
				Location location = event.getEntity().getLocation();
				String locationString = "X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: "
						+ location.getBlockZ();

				String msg = this.message.replace("%location%", locationString).replace("%world%",
						location.getWorld().getName());
				Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(msg));
			}
		}
	}
}
