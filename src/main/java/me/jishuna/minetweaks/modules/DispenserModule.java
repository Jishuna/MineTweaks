package me.jishuna.minetweaks.modules;

import java.util.EnumMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.data.CraftBlockData;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.api.module.TweakModule;
import net.minecraft.core.BlockPosition;

public class DispenserModule extends TweakModule {
	private EnumMap<Material, Material> placementMap;

	public DispenserModule(JavaPlugin plugin) {
		super(plugin, "dispensers");

		addSubModule("allow-place-blocks");
		addSubModule("bonemeal-sugarcane");
		addSubModule("bonemeal-sand");
		addSubModule("bonemeal-redsand");
		addSubModule("fill-cauldron-water");
		addSubModule("fill-cauldron-lava");
		addSubModule("fill-cauldron-snow");
		addSubModule("empty-cauldron-water");
		addSubModule("empty-cauldron-lava");
		addSubModule("empty-cauldron-snow");

		addEventHandler(BlockDispenseEvent.class, this::onDispense);
	}

	@Override
	public void reload() {
		super.reload();

		placementMap = new EnumMap<>(Material.class);

		ConfigurationSection section = getConfig().getConfigurationSection("placement-mappings");
		for (String key : section.getKeys(true)) {
			Material fromMaterial = Material.matchMaterial(key.toUpperCase());
			Material toMaterial = Material.matchMaterial(section.getString(key).toUpperCase());

			if (fromMaterial != null) {
				placementMap.put(fromMaterial, toMaterial);
			}
		}
	}

	private void onDispense(BlockDispenseEvent event) {
		if (!isEnabled() || event.getBlock().getType() != Material.DISPENSER)
			return;

		ItemStack item = event.getItem();
		Directional directional = (Directional) event.getBlock().getBlockData();
		BlockFace face = directional.getFacing();
		Block target = event.getBlock().getRelative(face);

		// Cauldrons
		if (target.getType() == Material.CAULDRON) {
			if (item.getType() == Material.WATER_BUCKET && getBoolean("fill-cauldron-water", true)) {
				updateCauldron(event, item, target, Material.WATER_CAULDRON, Material.BUCKET);
				Levelled cauldron = (Levelled) target.getBlockData();
				cauldron.setLevel(cauldron.getMaximumLevel());
				target.setBlockData(cauldron);
				return;
			} else if (item.getType() == Material.LAVA_BUCKET && getBoolean("fill-cauldron-lava", true)) {
				updateCauldron(event, item, target, Material.LAVA_CAULDRON, Material.BUCKET);
				return;
			} else if (item.getType() == Material.POWDER_SNOW_BUCKET && getBoolean("fill-cauldron-snow", true)) {
				updateCauldron(event, item, target, Material.POWDER_SNOW_CAULDRON, Material.BUCKET);
				return;
			}
		} else if (item.getType() == Material.BUCKET) {
			if (target.getType() == Material.WATER_CAULDRON && getBoolean("empty-cauldron-water", true)) {
				Levelled cauldron = (Levelled) target.getBlockData();
				if (cauldron.getLevel() >= cauldron.getMaximumLevel()) {
					updateCauldron(event, item, target, Material.CAULDRON, Material.WATER_BUCKET);
				}
				return;
			} else if (target.getType() == Material.LAVA_CAULDRON && getBoolean("empty-cauldron-lava", true)) {
				updateCauldron(event, item, target, Material.CAULDRON, Material.LAVA_BUCKET);
				return;
			} else if (target.getType() == Material.POWDER_SNOW_CAULDRON && getBoolean("empty-cauldron-snow", true)) {
				updateCauldron(event, item, target, Material.CAULDRON, Material.POWDER_SNOW_BUCKET);
				return;
			}
		}

		// Bonemeal
		if (item.getType() == Material.BONE_MEAL) {
			if (target.getType() == Material.SUGAR_CANE && getBoolean("bonemeal-sugarcane", true)) {
				if (FarmingModule.handleTallPlant(item, target, getInt("sugarcane-bonemeal-height", 3))) {
					removeUsedItem(event.getBlock(), item);
				}
			} else if (face != BlockFace.DOWN && target.getType() == Material.SAND
					&& getBoolean("bonemeal-sand", true)) {
				FarmingModule.handleSand(item, target);
				removeUsedItem(event.getBlock(), item);
			} else if (face != BlockFace.DOWN && target.getType() == Material.RED_SAND
					&& getBoolean("bonemeal-redsand", true)) {
				FarmingModule.handleSand(item, target);
				removeUsedItem(event.getBlock(), item);
			}
		}

		// Block placing
		if (getBoolean("allow-place-blocks", true)) {
			Material material = item.getType();

			if (this.placementMap.containsKey(material)) {
				material = this.placementMap.get(material);
			}

			if (material == null || !material.isBlock())
				return;

			event.setCancelled(true);

			// TODO NMS
			if (!((CraftBlockData) material.createBlockData()).getState().canPlace(
					((CraftWorld) target.getWorld()).getHandle(),
					new BlockPosition(target.getX(), target.getY(), target.getZ())))
				return;

			if (!target.getType().isAir() && getBoolean("placement-requires-air", true))
				return;

			target.setType(material);
			removeUsedItem(event.getBlock(), item);
		}
	}

	private void updateCauldron(BlockDispenseEvent event, ItemStack item, Block target, Material material,
			Material toAdd) {
		target.setType(material);

		event.setCancelled(true);

		Bukkit.getScheduler().runTask(getOwningPlugin(), () -> {
			Container state = (Container) event.getBlock().getState();
			state.getSnapshotInventory().removeItem(item);
			state.getSnapshotInventory().addItem(new ItemStack(toAdd));
			state.update();
		});
	}

	private void removeUsedItem(Block block, ItemStack item) {
		item.setAmount(1);

		Bukkit.getScheduler().runTask(getOwningPlugin(), () -> {
			Container state = (Container) block.getState();
			state.getSnapshotInventory().removeItem(item);
			state.update();
		});
	}
}