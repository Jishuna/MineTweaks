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

@RegisterTweak("dispenser_sugarcane_bonemealing")
public class DispenserSugarcaneBonemealTweak extends Tweak {
	private int height;

	public DispenserSugarcaneBonemealTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockDispenseEvent.class, EventPriority.HIGH, this::onDispense);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Dispensers/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.height = config.getInt("sugarcane-bonemeal-height", 3);
		});
	}

	private void onDispense(BlockDispenseEvent event) {
		if (event.isCancelled() || event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		if (item.getType() == Material.BONE_MEAL && target.getType() == Material.SUGAR_CANE
				&& FarmingUtils.handleTallPlant(item, target, height, GameMode.SURVIVAL)) {
			DispenserUtils.removeUsedItem(getPlugin(), event.getBlock(), item);
		}
	}
}
