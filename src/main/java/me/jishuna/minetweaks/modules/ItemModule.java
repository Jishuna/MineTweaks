package me.jishuna.minetweaks.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemBuilder;
import me.jishuna.commonlib.items.ItemUtils;
import me.jishuna.minetweaks.api.PluginKeys;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class ItemModule extends TweakModule {
	private List<Recipe> customRecipes;

	public ItemModule(JavaPlugin plugin) {
		super(plugin, "items");

		addSubModule("trowel");
		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		super.reload();

		if (this.customRecipes != null) {
			this.customRecipes.forEach(recipe -> {
				if (recipe instanceof Keyed keyed) {
					Bukkit.removeRecipe(keyed.getKey());
				}
			});
		}

		this.customRecipes = new ArrayList<>();

		if (!isEnabled())
			return;

		if (getBoolean("trowel", true)) {
			ItemStack trowel = new ItemBuilder(
					ItemUtils.getMaterial(getString("trowel-material", ""), Material.IRON_SHOVEL))
							.withName(ChatColor.RESET + getString("trowel-name", "Trowel"))
							.withModelData(getInt("trowel-model-data", 1))
							.withPersistantData(PluginKeys.ITEM_TYPE.getKey(), PersistentDataType.STRING, "trowel")
							.build();

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "trowel_recipe"), trowel);
			recipe.shape("100", "022");
			recipe.setIngredient('1', Material.STICK);
			recipe.setIngredient('2', Material.IRON_INGOT);
			addRecipe(recipe);
		}
	}

	private void addRecipe(Recipe recipe) {
		try {
			Bukkit.addRecipe(recipe);
			this.customRecipes.add(recipe);
		} catch (IllegalStateException ex) {
			if (recipe instanceof Keyed keyed)
				getOwningPlugin().getLogger().warning("Ignoring duplicate recipe: " + keyed.getKey());
		}
	}

	private void onInteract(PlayerInteractEvent event) {
		if (!isEnabled() || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null || !item.hasItemMeta())
			return;

		if (getBoolean("trowel", true) && item.getItemMeta().getPersistentDataContainer()
				.getOrDefault(PluginKeys.ITEM_TYPE.getKey(), PersistentDataType.STRING, "").equals("trowel")) {
			Random random = ThreadLocalRandom.current();
			Inventory inv = event.getPlayer().getInventory();
			ItemStack toUse = inv.getItem(random.nextInt(9));
			int tries = 10;

			Block target = block.getRelative(BlockFace.UP);

			if (!target.getType().isAir())
				return;

			event.setUseInteractedBlock(Result.DENY);

			while (tries > 0 && (toUse == null || !toUse.getType().isBlock())) {
				toUse = inv.getItem(random.nextInt(9));
				tries--;
			}

			if (toUse != null && toUse.getType().isBlock()) {
				block.getRelative(BlockFace.UP).setType(toUse.getType());
				toUse.setAmount(toUse.getAmount() - 1);
				ItemUtils.reduceDurability(event.getPlayer(), item, event.getHand());
			}
		}
	}
}
