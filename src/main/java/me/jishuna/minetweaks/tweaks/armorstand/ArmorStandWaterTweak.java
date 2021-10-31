package me.jishuna.minetweaks.tweaks.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "armorstand_water_cleaning")
public class ArmorStandWaterTweak extends Tweak {

	public ArmorStandWaterTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PotionSplashEvent.class, this::onPotionSplash);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Armor Stands/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onPotionSplash(PotionSplashEvent event) {
		ThrownPotion potion = event.getPotion();

		if (potion.getEffects().isEmpty()) {

			for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
				if (entity instanceof ArmorStand stand)
					stand.setVisible(true);
			}
		}
	}
}
