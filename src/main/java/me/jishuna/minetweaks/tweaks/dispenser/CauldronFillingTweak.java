package me.jishuna.minetweaks.tweaks.dispenser;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.DispenserUtils;

@RegisterTweak("cauldron_filling")
public class CauldronFillingTweak extends Tweak {

	public CauldronFillingTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockDispenseEvent.class, EventPriority.HIGH, this::onDispense);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Dispensers/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onDispense(BlockDispenseEvent event) {
		if (event.isCancelled() || event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		if (target.getType() == Material.CAULDRON) {
			if (item.getType() == Material.WATER_BUCKET) {
				DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.WATER_CAULDRON,
						Material.BUCKET);
				Levelled cauldron = (Levelled) target.getBlockData();
				cauldron.setLevel(cauldron.getMaximumLevel());
				target.setBlockData(cauldron);
			} else if (item.getType() == Material.LAVA_BUCKET) {
				DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.LAVA_CAULDRON,
						Material.BUCKET);
			} else if (item.getType() == Material.POWDER_SNOW_BUCKET) {
				DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.POWDER_SNOW_CAULDRON,
						Material.BUCKET);
				Levelled cauldron = (Levelled) target.getBlockData();
				cauldron.setLevel(cauldron.getMaximumLevel());
				target.setBlockData(cauldron);
			}
		}
	}
}
