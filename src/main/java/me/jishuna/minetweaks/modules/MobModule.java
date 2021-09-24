package me.jishuna.minetweaks.modules;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Snowman;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.util.internal.ThreadLocalRandom;
import me.jishuna.minetweaks.api.module.TweakModule;

public class MobModule extends TweakModule {

	public MobModule(JavaPlugin plugin) {
		super(plugin, "mobs");

		addEventHandler(EntityChangeBlockEvent.class, this::onBlockChange);
		addEventHandler(EntityExplodeEvent.class, this::onEntityExplode);
		addEventHandler(EntityInteractEvent.class, this::onEntityTrample);
		addEventHandler(PlayerInteractEntityEvent.class, this::onInteract);
	}

	private void onInteract(PlayerInteractEntityEvent event) {
		if (getBoolean("replace-snowman-head", true) && event.getRightClicked()instanceof Snowman snowman
				&& snowman.isDerp()) {
			ItemStack item;

			if (event.getHand() == EquipmentSlot.HAND) {
				item = event.getPlayer().getEquipment().getItemInMainHand();
			} else {
				item = event.getPlayer().getEquipment().getItemInOffHand();
			}

			if (item.getType() == Material.CARVED_PUMPKIN) {
				event.setCancelled(true);
				snowman.setDerp(false);
				item.setAmount(item.getAmount() - 1);
			}
		}
	}

	private void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.CREEPER && getBoolean("firework-creepers", false)) {
			event.blockList().clear();

			Firework firework = event.getEntity().getWorld().spawn(event.getLocation(), Firework.class);
			Random random = ThreadLocalRandom.current();

			FireworkMeta meta = firework.getFireworkMeta();

			int count = random.nextInt(3) + 1;
			for (int i = 0; i < count; i++) {
				FireworkEffect.Builder builder = FireworkEffect.builder().flicker(random.nextBoolean())
						.trail(random.nextBoolean()).with(Type.BURST).withColor(getRandomColor(random));

				if (random.nextBoolean()) {
					builder.withFade(getRandomColor(random));
				}
				meta.addEffect(builder.build());
			}

			firework.setFireworkMeta(meta);
			firework.detonate();
		}
	}

	private void onBlockChange(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN && getBoolean("disable-endermen-griefing", true)) {
			event.setCancelled(true);
		}
	}

	private void onEntityTrample(EntityInteractEvent event) {
		if (getBoolean("no-trampling-farmland", true) && event.getEntityType() != EntityType.PLAYER
				&& event.getBlock().getType() == Material.FARMLAND) {
			event.setCancelled(true);
		}
	}

	private Color getRandomColor(Random random) {
		return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}
}