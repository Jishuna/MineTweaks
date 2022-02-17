package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("slimeball_sticky_pistons")
public class StickyPistonConversionTweak extends Tweak {

	public StickyPistonConversionTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null)
			return;

		if (block.getType() == Material.PISTON && item.getType() == Material.SLIME_BALL) {
			Directional oldData = (Directional) block.getBlockData();

			block.setType(Material.STICKY_PISTON);
			Directional data = (Directional) block.getBlockData();
			data.setFacing(oldData.getFacing());
			block.setBlockData(data);

			if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
				item.setAmount(item.getAmount() - 1);
			
			event.getPlayer().playSound(block.getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1f, 1f);
		}
	}
}
