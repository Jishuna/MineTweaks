package me.jishuna.minetweaks.modules;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.api.module.TweakModule;

public class ArmorstandModule extends TweakModule {

	public ArmorstandModule(JavaPlugin plugin) {
		super(plugin, "armor_stands");

		addEventHandler(EntitySpawnEvent.class, this::onEntitySpawn);
		addEventHandler(PlayerInteractAtEntityEvent.class, this::onEntityInteract);
	}

	private void onEntitySpawn(EntitySpawnEvent event) {
		if (getBoolean("armorstand-arms", true) && event.getEntity()instanceof ArmorStand stand) {
			stand.setArms(true);
		}
	}

	private void onEntityInteract(PlayerInteractAtEntityEvent event) {
		if (event.isCancelled() || !isEnabled())
			return;
		
		if (event.getPlayer().getEquipment().getItemInMainHand().getType().isAir() && getBoolean("armorstand-offhand", true)
				&& event.getRightClicked() instanceof ArmorStand stand) {
			
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
}
