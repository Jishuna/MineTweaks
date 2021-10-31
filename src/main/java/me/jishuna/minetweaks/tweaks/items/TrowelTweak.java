package me.jishuna.minetweaks.tweaks.items;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemBuilder;
import me.jishuna.commonlib.items.ItemUtils;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.PluginKeys;
import me.jishuna.minetweaks.api.RecipeManager;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.CustomItemUtils;
import net.md_5.bungee.api.ChatColor;

@RegisterTweak(name = "trowel")
public class TrowelTweak extends Tweak {
	public TrowelTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			if (!isEnabled())
				return;

			ItemStack trowel = new ItemBuilder(
					ItemUtils.getMaterial(config.getString("trowel-material", ""), Material.IRON_SHOVEL))
							.withName(ChatColor.RESET + config.getString("trowel-name", "Trowel"))
							.withModelData(config.getInt("trowel-model-data", 1))
							.withPersistantData(PluginKeys.ITEM_TYPE.getKey(), PersistentDataType.STRING, "trowel")
							.build();

			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getOwningPlugin(), "trowel_recipe"), trowel);
			recipe.shape("100", "022");
			recipe.setIngredient('1', Material.STICK);
			recipe.setIngredient('2', Material.IRON_INGOT);
			RecipeManager.getInstance().addRecipe(getOwningPlugin(), recipe);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null || !item.hasItemMeta())
			return;

		if (CustomItemUtils.getCustomItemType(item).equals("trowel")) {
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

				if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
					toUse.setAmount(toUse.getAmount() - 1);
					ItemUtils.reduceDurability(event.getPlayer(), item, event.getHand());
				}
			}
		}
	}
}
