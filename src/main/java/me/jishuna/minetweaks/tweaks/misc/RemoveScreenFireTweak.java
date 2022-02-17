package me.jishuna.minetweaks.tweaks.misc;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("remove_screen_fire")
public class RemoveScreenFireTweak extends Tweak {
	public RemoveScreenFireTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityCombustEvent.class, this::onCombust);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onCombust(EntityCombustEvent event) {
		if (event.getEntity() instanceof Player player
				&& (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)
						|| player.getGameMode() == GameMode.CREATIVE)) {
			event.setCancelled(true);
			player.setFireTicks(-20);
		}
	}
}
