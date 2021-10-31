package me.jishuna.minetweaks.tweaks.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemBuilder;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "infinite_water_bucket")
public class InfiniteWaterBucketTweak extends Tweak {
	private static final ItemStack BUCKET = new ItemBuilder(Material.WATER_BUCKET)
			.withEnchantment(Enchantment.ARROW_INFINITE, 1).build();
	private int cost;

	public InfiniteWaterBucketTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerBucketEmptyEvent.class, this::onBucket);
		addEventHandler(PrepareAnvilEvent.class, this::onAnvil);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Items/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.cost = config.getInt("anvil-level-cost", 20);
		});
	}

	private void onAnvil(PrepareAnvilEvent event) {
		AnvilInventory inventory = event.getInventory();
		ItemStack first = inventory.getItem(0);
		ItemStack second = inventory.getItem(1);

		if (first == null || first.getType() != Material.WATER_BUCKET || second == null
				|| second.getType() != Material.ENCHANTED_BOOK)
			return;

		if (first.containsEnchantment(Enchantment.ARROW_INFINITE))
			return;

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) second.getItemMeta();
		if (meta.hasStoredEnchant(Enchantment.ARROW_INFINITE)) {
			event.setResult(ItemBuilder.modifyItem(BUCKET.clone()).withName(inventory.getRenameText()).build());
			inventory.setRepairCost(this.cost);
		}
	}

	private void onBucket(PlayerBucketEmptyEvent event) {
		EntityEquipment equipment = event.getPlayer().getEquipment();
		for (ItemStack item : new ItemStack[] { equipment.getItemInMainHand(), equipment.getItemInOffHand() }) {
			if (item == null || item.getType() != Material.WATER_BUCKET)
				continue;

			if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
				event.setItemStack(item);
				break;
			}
		}
	}
}
