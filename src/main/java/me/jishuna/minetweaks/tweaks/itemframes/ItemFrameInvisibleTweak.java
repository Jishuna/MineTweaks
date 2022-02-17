package me.jishuna.minetweaks.tweaks.itemframes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("itemframe_invisibility")
public class ItemFrameInvisibleTweak extends Tweak {
	public ItemFrameInvisibleTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(PotionSplashEvent.class, EventPriority.HIGH, this::onPotionSplash);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Item Frames/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onPotionSplash(PotionSplashEvent event) {
		if (event.isCancelled())
			return;
		
		ThrownPotion potion = event.getPotion();

		for (PotionEffect effect : potion.getEffects()) {
			if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {

				for (Entity entity : potion.getNearbyEntities(3.5, 2, 3.5)) {
					if (entity instanceof ItemFrame frame)
						frame.setVisible(false);

				}
				break;
			}
		}
	}
}
