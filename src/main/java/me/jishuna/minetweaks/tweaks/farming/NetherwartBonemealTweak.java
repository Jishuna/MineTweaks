package me.jishuna.minetweaks.tweaks.farming;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.FarmingUtils;

@RegisterTweak("netherwart_bonemealing")
public class NetherwartBonemealTweak extends Tweak {

	public NetherwartBonemealTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Farming/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item != null && item.getType() == Material.BONE_MEAL && block.getType() == Material.NETHER_WART) {
			FarmingUtils.handlePlant(item, block, event.getPlayer().getGameMode());
		}
	}
}
