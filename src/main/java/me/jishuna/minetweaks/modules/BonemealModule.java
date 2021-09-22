package me.jishuna.minetweaks.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.api.module.TweakModule;

public class BonemealModule extends TweakModule {

	public BonemealModule(JavaPlugin plugin) {
		super(plugin, "bonemeal");

		addEventHandler(PlayerInteractEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEvent event) {
		if (!isEnabled() || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		ItemStack item = event.getItem();
		if (item == null || item.getType() != Material.BONE_MEAL)
			return;

		Block block = event.getClickedBlock();

		if (block.getType() == Material.CACTUS && getBoolean("bonemeal-cactus", true)) {
			handleTallPlant(item, block, getInt("cactus-bonemeal-height", 3));
		} else if (block.getType() == Material.SUGAR_CANE && getBoolean("bonemeal-sugarcane", true)) {
			handleTallPlant(item, block, getInt("sugarcane-bonemeal-height", 3));
		} else if (block.getType() == Material.SAND && getBoolean("bonemeal-sand", true)) {
			handleSand(item, block);
		} else if (block.getType() == Material.RED_SAND && getBoolean("bonemeal-redsand", true)) {
			handleSand(item, block);
		}
	}

	// Handles growing dead bushes on sand
	public static void handleSand(ItemStack item, Block block) {
		Random random = ThreadLocalRandom.current();
		block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 1.5, 0.5), 15, 3d, 0.5d, 3d);
		
		item.setAmount(item.getAmount() - 1);

		for (int i = 0; i < 15; i++) {
			Location location = block.getLocation();
			location.add(random.nextGaussian() * 2, 3, random.nextGaussian() * 2);

			for (int j = 0; j < 6; j++) {
				Block current = location.getBlock();
				if (current.getType().isAir() && current.getRelative(BlockFace.DOWN).getType() == block.getType()) {
					current.setType(Material.DEAD_BUSH);
					break;
				}
				location.subtract(0, 1, 0);
			}
		}
	}

	// Handles bonemealing cactus and sugar cane
	public static void handleTallPlant(ItemStack item, Block block, int maxHeight) {
		TallPlantData data = evaluateTallPlantData(block, new TallPlantData(block), null);

		Block top = data.getTopBlock();
		Ageable blockData = (Ageable) top.getBlockData();
		int age = blockData.getAge();

		age += ThreadLocalRandom.current().nextInt(4) + 2;
		blockData.setAge(Math.min(age, blockData.getMaximumAge()));
		top.setBlockData(blockData);

		if (blockData.getAge() == age) {
			item.setAmount(item.getAmount() - 1);
			block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 15, 0.3d, 0.3d, 0.3d);
		}

		if (age > blockData.getMaximumAge() && data.getHeight() < maxHeight) {
			block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 15, 0.3d, 0.3d, 0.3d);
			top.getRelative(BlockFace.UP).setType(top.getType());
			item.setAmount(item.getAmount() - 1);
		}
	}

	// Handles detecting height and top block of sugar cane
	public static TallPlantData evaluateTallPlantData(Block block, TallPlantData data, BlockFace face) {
		if (face == null || face == BlockFace.UP) {
			Block nextBlock = block.getRelative(BlockFace.UP);
			if (nextBlock.getType() == block.getType()) {
				data.addHeight();
				data.setTopBlock(nextBlock);
				evaluateTallPlantData(nextBlock, data, BlockFace.UP);
			}
		}

		if (face == null || face == BlockFace.DOWN) {
			Block nextBlock = block.getRelative(BlockFace.DOWN);
			if (nextBlock.getType() == block.getType()) {
				data.addHeight();
				evaluateTallPlantData(nextBlock, data, BlockFace.DOWN);
			}
		}
		return data;
	}

	public static class TallPlantData {
		private Block topBlock;
		private int height;

		public TallPlantData(Block block) {
			this.topBlock = block;
			this.height = 1;
		}

		public Block getTopBlock() {
			return topBlock;
		}

		public void setTopBlock(Block topBlock) {
			this.topBlock = topBlock;
		}

		public int getHeight() {
			return height;
		}

		public void addHeight() {
			this.height++;
		}
	}
}
