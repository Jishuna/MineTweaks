package me.jishuna.minetweaks.tweaks.farming;

import java.util.Collection;
import java.util.EnumMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("right_click_harvesting")
public class RightClickHarvestTweak extends Tweak {
	private EnumMap<Material, Material> seedMap;

	public RightClickHarvestTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
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
		Material seed = this.seedMap.get(block.getType());

		if (data instanceof Ageable ageable && seed != null && ageable.getAge() >= ageable.getMaximumAge()) {
			Collection<ItemStack> drops = block.getDrops();

			for (ItemStack item : drops) {
				if (item.getType() == seed) {
					item.setAmount(item.getAmount() - 1);
					break;
				}
			}

			drops.forEach(item -> {
				if (item.getAmount() > 0)
					block.getWorld().dropItemNaturally(block.getLocation(), item);
			});

			ageable.setAge(0);
			block.setBlockData(ageable);
			event.setCancelled(true);
		}
	}
}
