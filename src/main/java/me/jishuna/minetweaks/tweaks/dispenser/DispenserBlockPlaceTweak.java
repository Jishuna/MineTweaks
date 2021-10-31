package me.jishuna.minetweaks.tweaks.dispenser;

import java.util.EnumMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.DispenserUtils;
import me.jishuna.minetweaks.api.util.NMSUtil;

@RegisterTweak(name = "dispenser_block_placing")
public class DispenserBlockPlaceTweak extends Tweak {
	private EnumMap<Material, Material> placementMap;
	private boolean requireAir;

	public DispenserBlockPlaceTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockDispenseEvent.class, this::onDispense);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Dispensers/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.requireAir = config.getBoolean("placement-requires-air", true);

			placementMap = new EnumMap<>(Material.class);

			ConfigurationSection section = config.getConfigurationSection("placement-mappings");
			for (String key : section.getKeys(true)) {
				Material fromMaterial = Material.matchMaterial(key.toUpperCase());
				Material toMaterial = Material.matchMaterial(section.getString(key).toUpperCase());

				if (fromMaterial != null) {
					placementMap.put(fromMaterial, toMaterial);
				}
			}
		});
	}

	private void onDispense(BlockDispenseEvent event) {
		if (event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		Material material = item.getType();

		if (this.placementMap.containsKey(material)) {
			material = this.placementMap.get(material);
		}

		if (material == null || !material.isBlock())
			return;

		event.setCancelled(true);

		if (!NMSUtil.canPlace(material, target.getLocation()))
			return;

		if (!target.getType().isAir() && requireAir)
			return;

		target.setType(material);
		DispenserUtils.removeUsedItem(getOwningPlugin(), event.getBlock(), item);
	}
}
