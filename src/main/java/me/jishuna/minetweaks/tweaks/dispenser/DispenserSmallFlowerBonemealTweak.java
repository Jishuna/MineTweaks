package me.jishuna.minetweaks.tweaks.dispenser;

import java.util.HashSet;
import java.util.Set;

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
import me.jishuna.minetweaks.tweaks.farming.SmallFlowerBonemealTweak;

@RegisterTweak("dispenser_small_flower_bonemealing")
public class DispenserSmallFlowerBonemealTweak extends Tweak {
	private Set<Material> flowers;

	public DispenserSmallFlowerBonemealTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(BlockDispenseEvent.class, EventPriority.HIGH, this::onDispense);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Dispensers/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.flowers = new HashSet<>();

			for (String key : config.getStringList("flowers")) {
				Material material = Material.matchMaterial(key.toUpperCase());

				if (material != null) {
					flowers.add(material);
				}
			}
		});
	}

	private void onDispense(BlockDispenseEvent event) {
		if (event.isCancelled() || event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		if (item.getType() == Material.BONE_MEAL && this.flowers.contains(target.getType())) {
			SmallFlowerBonemealTweak.handleFlower(item, target, GameMode.SURVIVAL);
			DispenserUtils.removeUsedItem(getPlugin(), event.getBlock(), item);
		}
	}
}
