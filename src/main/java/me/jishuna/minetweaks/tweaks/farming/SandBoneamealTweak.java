package me.jishuna.minetweaks.tweaks.farming;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.FarmingUtils;

@RegisterTweak(name = "sand_bonemealing")
public class SandBoneamealTweak extends Tweak {
	private boolean sand;
	private boolean redSand;

	public SandBoneamealTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Farming/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.sand = config.getBoolean("allow-normal-sand", true);
			this.redSand = config.getBoolean("allow-red-sand", true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item != null && item.getType() == Material.BONE_MEAL) {
			if (block.getType() == Material.SAND && sand) {
				FarmingUtils.handleSand(item, block);
			} else if (block.getType() == Material.RED_SAND && redSand) {
				FarmingUtils.handleSand(item, block);
			}
		}
	}
}
