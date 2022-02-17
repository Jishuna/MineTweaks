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

@RegisterTweak("cauldron_emptying")
public class CauldronEmptyingTweak extends Tweak {

	public CauldronEmptyingTweak(MineTweaks plugin, String name) {
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

		if (item.getType() == Material.BUCKET) {
			if (target.getType() == Material.WATER_CAULDRON) {
				Levelled cauldron = (Levelled) target.getBlockData();
				if (cauldron.getLevel() >= cauldron.getMaximumLevel()) {
					DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.CAULDRON,
							Material.WATER_BUCKET);
				}
			} else if (target.getType() == Material.LAVA_CAULDRON) {
				DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.CAULDRON,
						Material.LAVA_BUCKET);
			} else if (target.getType() == Material.POWDER_SNOW_CAULDRON) {
				Levelled cauldron = (Levelled) target.getBlockData();
				if (cauldron.getLevel() >= cauldron.getMaximumLevel()) {
					DispenserUtils.updateCauldron(getPlugin(), event, item, target, Material.CAULDRON,
							Material.POWDER_SNOW_BUCKET);
				}
			}
		}
	}
}
