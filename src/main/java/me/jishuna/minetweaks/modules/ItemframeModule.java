package me.jishuna.minetweaks.modules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.minetweaks.api.module.TweakModule;

public class ItemframeModule extends TweakModule {

	public ItemframeModule(JavaPlugin plugin) {
		super(plugin, "item_frames");

		addEventHandler(PotionSplashEvent.class, this::onPotionSplash);
	}

	private void onPotionSplash(PotionSplashEvent event) {
		if (!isEnabled())
			return;
		
		ThrownPotion potion = event.getPotion();

		if (potion.getEffects().isEmpty() && getBoolean("water-bottle-frames", true)) {

			for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
				if (entity instanceof ItemFrame frame) {
					frame.setVisible(true);
				}
			}
			return;
		}

		for (PotionEffect effect : potion.getEffects()) {
			if (effect.getType().equals(PotionEffectType.INVISIBILITY)
					&& getBoolean("invisibilty-potion-frames", true)) {

				for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
					if (entity instanceof ItemFrame frame) {
						frame.setVisible(false);
					}
				}
				break;
			}
		}
	}
}
