package me.jishuna.minetweaks.tweaks.farming;

import java.util.EnumMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("right_click_harvesting")
public class RightClickHarvestTweak extends Tweak {
	private EnumMap<Material, Material> seedMap;
	private Location harvesting;
	private Material harvestingType;

	public RightClickHarvestTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
		addEventHandler(BlockDropItemEvent.class, EventPriority.HIGH, this::onDrop);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Farming/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			seedMap = new EnumMap<>(Material.class);

			ConfigurationSection section = config.getConfigurationSection("harvest-seed-mappings");
			for (String key : section.getKeys(true)) {
				Material fromMaterial = Material.matchMaterial(key.toUpperCase());
				Material toMaterial = Material.matchMaterial(section.getString(key).toUpperCase());

				if (fromMaterial != null) {
					seedMap.put(fromMaterial, toMaterial);
				}
			}
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		BlockData data = block.getBlockData();

		if (data instanceof Ageable ageable && ageable.getAge() >= ageable.getMaximumAge()) {
			Material mat = block.getType();
			event.setCancelled(true);
			
			if (event.getPlayer().breakBlock(block)) {
				harvesting = block.getLocation();
				harvestingType = mat;
				block.setType(mat);
			}
		}

	}

	private void onDrop(BlockDropItemEvent event) {
		if (harvesting == null || !harvesting.equals(event.getBlock().getLocation()))
			return;

		Material seed = this.seedMap.get(harvestingType);

		harvesting = null;
		harvestingType = null;
		if (seed == null)
			return;

		for (Item item : event.getItems()) {
			ItemStack itemstack = item.getItemStack();
			if (itemstack.getType() == seed) {
				itemstack.setAmount(itemstack.getAmount() - 1);
				if (itemstack.getAmount() <= 0) {
					item.remove();
				} else {
					item.setItemStack(itemstack);
				}
				break;
			}
		}
	}
}
