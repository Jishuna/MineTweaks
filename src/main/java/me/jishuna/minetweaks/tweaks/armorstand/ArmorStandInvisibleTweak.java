package me.jishuna.minetweaks.tweaks.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "armorstand_invisibility")
public class ArmorStandInvisibleTweak extends Tweak {

	public ArmorStandInvisibleTweak(JavaPlugin plugin, String name) {
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

		for (PotionEffect effect : potion.getEffects()) {
			if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {

				for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
					if (entity instanceof ArmorStand stand)
						stand.setVisible(false);

				}
				break;
			}
		}
	}
}
