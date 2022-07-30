package me.jishuna.minetweaks.tweaks.farming;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
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

@RegisterTweak("small_flower_bonemealing")
public class SmallFlowerBonemealTweak extends Tweak {
	private Set<Material> flowers;

	public SmallFlowerBonemealTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Farming/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.flowers = new HashSet<>();

			for (String key : config.getStringList("flowers")) {
				Material material = Material.matchMaterial(key.toUpperCase());

				if (material != null) {
					flowers.add(material);
				}
			}
		});
	}

	private void onInteract(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();
		ItemStack item = event.getItem();

		if (item != null && item.getType() == Material.BONE_MEAL && this.flowers.contains(block.getType())) {
			handleFlower(item, block, event.getPlayer().getGameMode());
		}
	}

	public static void handleFlower(ItemStack item, Block block, GameMode mode) {
		World world = block.getWorld();
		Location location = block.getLocation().add(0.5, 0.5, 0.5);
		world.dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
		world.spawnParticle(Particle.VILLAGER_HAPPY, location, 15, 0.3d, 0.3d, 0.3d);

		if (mode != GameMode.CREATIVE) {
			item.setAmount(item.getAmount() - 1);
		}
	}
}
