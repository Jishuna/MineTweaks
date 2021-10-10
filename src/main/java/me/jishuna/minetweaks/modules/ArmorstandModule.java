package me.jishuna.minetweaks.modules;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.minetweaks.api.module.TweakModule;

public class ArmorstandModule extends TweakModule {

	public ArmorstandModule(JavaPlugin plugin) {
		super(plugin, "armor_stands");

		addSubModule("armorstand-arms");
		addSubModule("armorstand-offhand");
		addSubModule("water-bottle-armor-stands");
		addSubModule("invisibility-potion-armor-stands");

		addEventHandler(CreatureSpawnEvent.class, this::onEntitySpawn);
		addEventHandler(PlayerInteractAtEntityEvent.class, this::onEntityInteract);
		addEventHandler(PotionSplashEvent.class, this::onPotionSplash);
	}

	private void onEntitySpawn(CreatureSpawnEvent event) {
		if (getBoolean("armorstand-arms", true) && event.getEntity()instanceof ArmorStand stand) {
			stand.setArms(true);
		}
	}

	private void onEntityInteract(PlayerInteractAtEntityEvent event) {
		if (event.isCancelled() || !isEnabled())
			return;

		if (event.getPlayer().getEquipment().getItemInMainHand().getType().isAir()
				&& getBoolean("armorstand-offhand", true) && event.getRightClicked()instanceof ArmorStand stand) {

			ItemStack current = stand.getEquipment().getItemInOffHand();
			if (current.getType().isAir()) {
				ItemStack item = event.getPlayer().getEquipment().getItemInOffHand();
				if (item.getType().isAir())
					return;

				ItemStack toHold = item.clone();
				toHold.setAmount(1);

				item.setAmount(item.getAmount() - 1);

				stand.getEquipment().setItemInOffHand(toHold);
			} else {
				event.getPlayer().getInventory().addItem(current);
				stand.getEquipment().setItemInOffHand(null);
			}
			event.setCancelled(true);
		}
	}

	private void onPotionSplash(PotionSplashEvent event) {
		if (!isEnabled())
			return;

		ThrownPotion potion = event.getPotion();

		if (potion.getEffects().isEmpty() && getBoolean("water-bottle-armor-stands", true)) {

			for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
				if (entity instanceof ArmorStand stand)
					stand.setVisible(true);

			}
			return;
		}

		for (PotionEffect effect : potion.getEffects()) {
			if (effect.getType().equals(PotionEffectType.INVISIBILITY)
					&& getBoolean("invisibility-potion-armor-stands", true)) {

				for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
					if (entity instanceof ArmorStand stand)
						stand.setVisible(false);

				}
				break;
			}
		}
	}
}
