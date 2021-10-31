package me.jishuna.minetweaks.tweaks.blocks;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "anvil_cobble_to_sand")
public class AnvilCobblestoneTweak extends Tweak {

	public AnvilCobblestoneTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityChangeBlockEvent.class, this::onBlockLand);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Blocks/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onBlockLand(EntityChangeBlockEvent event) {
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
