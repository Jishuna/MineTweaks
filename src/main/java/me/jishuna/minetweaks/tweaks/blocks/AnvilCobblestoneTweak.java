package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("anvil_cobble_to_sand")
public class AnvilCobblestoneTweak extends Tweak {

	public AnvilCobblestoneTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityChangeBlockEvent.class, EventPriority.HIGH, this::onBlockLand);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onBlockLand(EntityChangeBlockEvent event) {
		if (event.isCancelled())
			return;
		
		if (event.getEntity()instanceof FallingBlock block) {
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
