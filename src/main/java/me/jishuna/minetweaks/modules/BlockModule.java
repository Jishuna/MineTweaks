package me.jishuna.minetweaks.modules;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.items.ItemUtils;
import me.jishuna.minetweaks.api.module.TweakModule;
import me.jishuna.minetweaks.api.util.NMSUtil;

public class BlockModule extends TweakModule {

	private static final BlockFace[] FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH,
			BlockFace.WEST };

	public BlockModule(JavaPlugin plugin) {
		super(plugin, "blocks");

		addSubModule("anvil-cobble-to-sand");
		addSubModule("slimeball-sticky-pistons");
		addSubModule("fire-dry-sponge");
		addSubModule("downwards-ladders");

		addEventHandler(EntityChangeBlockEvent.class, this::onBlockLand);
		addEventHandler(PlayerInteractEvent.class, this::onInteract);
		addEventHandler(NotePlayEvent.class, this::onNotePlay);
	}

	private void onInteract(PlayerInteractEvent event) {
		if (!isEnabled() || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item == null)
			return;

		if (getBoolean("downwards-ladders", true) && block.getType() == Material.LADDER
				&& item.getType() == Material.LADDER) {
			Block target = block;
			Directional current = (Directional) block.getBlockData();
			current.setFacing(current.getFacing().getOppositeFace());

			while (target.getY() > block.getWorld().getMinHeight() && target.getType() == Material.LADDER) {
				target = target.getRelative(BlockFace.DOWN);
			}

			if (NMSUtil.canPlace(current, target.getRelative(current.getFacing()).getLocation())) {
				Directional ladder = (Directional) Material.LADDER.createBlockData();
				ladder.setFacing(current.getFacing().getOppositeFace());
				target.setBlockData(ladder);

				item.setAmount(item.getAmount() - 1);
			}
		}

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

	@SuppressWarnings("deprecation")
	private void onNotePlay(NotePlayEvent event) {
		if (!getBoolean("mob-head-noteblocks", true))
			return;

		Block block = event.getBlock();
		for (BlockFace face : FACES) {
			switch (block.getRelative(face).getType()) {
			case CREEPER_HEAD, CREEPER_WALL_HEAD:
				event.setCancelled(true);
				block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CREEPER_PRIMED, SoundCategory.RECORDS, 1f,
						event.getNote().getId() * 0.0833f);
				return;

			case SKELETON_SKULL, SKELETON_WALL_SKULL:
				event.setCancelled(true);
				block.getWorld().playSound(block.getLocation(), Sound.ENTITY_SKELETON_AMBIENT, SoundCategory.RECORDS,
						1f, event.getNote().getId() * 0.0833f);
				return;

			case ZOMBIE_HEAD, ZOMBIE_WALL_HEAD:
				event.setCancelled(true);
				block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ZOMBIE_AMBIENT, SoundCategory.RECORDS, 1f,
						event.getNote().getId() * 0.0833f);
				return;

			case WITHER_SKELETON_SKULL, WITHER_SKELETON_WALL_SKULL:
				event.setCancelled(true);
				block.getWorld().playSound(block.getLocation(), Sound.ENTITY_WITHER_SKELETON_AMBIENT,
						SoundCategory.RECORDS, 1f, event.getNote().getId() * 0.0833f);
				return;

			case DRAGON_HEAD, DRAGON_WALL_HEAD:
				event.setCancelled(true);
				block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.RECORDS,
						1f, event.getNote().getId() * 0.0833f);
				return;
			default:
				break;
			}
		}

	}
}
