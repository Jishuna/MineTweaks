package me.jishuna.minetweaks.tweaks.dispenser;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.DispenserUtils;
import me.jishuna.minetweaks.api.util.FarmingUtils;

@RegisterTweak("dispenser_sand_bonemealing")
public class DispenserSandBonemealTweak extends Tweak {
	private boolean sand;
	private boolean redSand;

	public DispenserSandBonemealTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockDispenseEvent.class, EventPriority.HIGH, this::onDispense);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Dispensers/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.sand = config.getBoolean("allow-normal-sand", true);
			this.redSand = config.getBoolean("allow-red-sand", true);
		});
	}

	private void onDispense(BlockDispenseEvent event) {
		if (event.isCancelled() || event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		if (item.getType() == Material.BONE_MEAL) {
			if (face != BlockFace.DOWN && target.getType() == Material.SAND && sand) {
				FarmingUtils.handleSand(item, target, GameMode.SURVIVAL);
				DispenserUtils.removeUsedItem(getPlugin(), event.getBlock(), item);
			} else if (face != BlockFace.DOWN && target.getType() == Material.RED_SAND && redSand) {
				FarmingUtils.handleSand(item, target, GameMode.SURVIVAL);
				DispenserUtils.removeUsedItem(getPlugin(), event.getBlock(), item);
			}
		}
	}
}
