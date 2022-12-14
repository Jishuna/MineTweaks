package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.NotePlayEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.Version;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("mob_head_noteblocks")
public class MobHeadNoteblockTweak extends Tweak {
	private static final BlockFace[] FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH,
			BlockFace.WEST };

	public MobHeadNoteblockTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addInvalidVersions(Version.V1_19_R2);
		addEventHandler(NotePlayEvent.class, EventPriority.HIGH, this::onNotePlay);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	@SuppressWarnings("deprecation")
	private void onNotePlay(NotePlayEvent event) {
		if (event.isCancelled())
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
