package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import me.jishuna.minetweaks.api.util.ColorUtils;

@RegisterTweak("shulker_dyeing")
public class ShulkerDyeingTweak extends Tweak {

	public ShulkerDyeingTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerInteractEntityEvent.class, EventPriority.HIGH, this::onInteract);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled())
			return;
		
		ItemStack item;
		if (event.getHand() == EquipmentSlot.HAND) {
			item = event.getPlayer().getEquipment().getItemInMainHand();
		} else {
			item = event.getPlayer().getEquipment().getItemInOffHand();
		}
		if (event.getRightClicked()instanceof Shulker shulker && item.getType().toString().endsWith("_DYE")) {
			DyeColor color = ColorUtils.dyeToDyeColor(item.getType());

			if (color != shulker.getColor()) {
				event.setCancelled(true);
				shulker.setColor(color);

				if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
					item.setAmount(item.getAmount() - 1);
			}
		}
	}
}
