package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.nms.NMSManager;

@RegisterTweak("downwards_ladders")
public class DownwardsLaddersTweak extends Tweak {
	private int maxDistance;

	public DownwardsLaddersTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.maxDistance = config.getInt("ladder-place-distance", 64);
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null)
			return;

		if (block.getType() == Material.LADDER && item.getType() == Material.LADDER) {
			Block target = block;
			Directional current = (Directional) block.getBlockData();

			for (int distance = 0; target.getY() > block.getWorld().getMinHeight()
					&& target.getType() == Material.LADDER && distance < this.maxDistance; distance++) {
				target = target.getRelative(BlockFace.DOWN);
			}

			if (target.getType().isAir() && NMSManager.getAdapter().canPlace(current, target.getLocation())) {
				BlockPlaceEvent placeEvent = new BlockPlaceEvent(block, block.getState(),
						block.getRelative(current.getFacing().getOppositeFace()), item, event.getPlayer(), true,
						event.getHand());

				Bukkit.getPluginManager().callEvent(placeEvent);
				if (placeEvent.isCancelled())
					return;

				Directional ladder = (Directional) Material.LADDER.createBlockData();
				ladder.setFacing(current.getFacing());
				target.setBlockData(ladder);

				if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
					item.setAmount(item.getAmount() - 1);
			}
		}
	}
}
