package me.jishuna.minetweaks.modules;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemUtils;
import me.jishuna.minetweaks.api.module.TweakModule;

public class BlockModule extends TweakModule {

	public BlockModule(JavaPlugin plugin) {
		super(plugin, "blocks");

		addSubModule("anvil-cobble-to-sand");
		addSubModule("slimeball-sticky-pistons");
		addSubModule("fire-dry-sponge");

		addEventHandler(EntityChangeBlockEvent.class, this::onBlockLand);
		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event) {
		if (!isEnabled() || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null)
			return;

		if (getBoolean("slimeball-sticky-pistons", true) && block.getType() == Material.PISTON
				&& item.getType() == Material.SLIME_BALL) {
			Directional oldData = (Directional) block.getBlockData();

			block.setType(Material.STICKY_PISTON);
			Directional data = (Directional) block.getBlockData();
			data.setFacing(oldData.getFacing());
			block.setBlockData(data);

			item.setAmount(item.getAmount() - 1);
			event.getPlayer().playSound(block.getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1f, 1f);
		}

		if (getBoolean("fire-dry-sponge", true) && block.getType() == Material.WET_SPONGE
				&& item.getType() == Material.FLINT_AND_STEEL) {
			block.setType(Material.SPONGE);
			event.setUseItemInHand(Result.DENY);
			ItemUtils.reduceDurability(event.getPlayer(), item, event.getHand());
		}
	}

	private void onBlockLand(EntityChangeBlockEvent event) {
		if (getBoolean("anvil-cobble-to-sand", true) && event.getEntity()instanceof FallingBlock block) {
			Material material = block.getBlockData().getMaterial();
			if (material != Material.ANVIL && material != Material.CHIPPED_ANVIL && material != Material.DAMAGED_ANVIL)
				return;

			Block target = event.getBlock().getRelative(BlockFace.DOWN);

			if (target.getType() == Material.COBBLESTONE) {
				target.setType(Material.SAND);
				target.getWorld().spawnParticle(Particle.BLOCK_DUST, target.getLocation().add(0.5, 0.5, 0.5), 25, 0.3,
						0.3, 0.3, Material.COBBLESTONE.createBlockData());
			}
		}
	}
}
