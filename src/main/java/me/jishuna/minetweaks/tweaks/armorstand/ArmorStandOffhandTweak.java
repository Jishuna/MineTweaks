package me.jishuna.minetweaks.tweaks.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "armorstand_offhand")
public class ArmorStandOffhandTweak extends Tweak {

	public ArmorStandOffhandTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractAtEntityEvent.class, this::onEntityInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Armor Stands/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onEntityInteract(PlayerInteractAtEntityEvent event) {
		if (event.isCancelled())
			return;

		if (event.getPlayer().getEquipment().getItemInMainHand().getType().isAir()
				&& event.getRightClicked()instanceof ArmorStand stand) {

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
