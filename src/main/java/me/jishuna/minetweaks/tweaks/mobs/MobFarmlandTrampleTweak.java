package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityInteractEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("no_trampling_farmland")
public class MobFarmlandTrampleTweak extends Tweak {

	public MobFarmlandTrampleTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityInteractEvent.class, this::onEntityTrample);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onEntityTrample(EntityInteractEvent event) {
		if (event.getEntityType() != EntityType.PLAYER && event.getBlock().getType() == Material.FARMLAND) {
			event.setCancelled(true);
		}
	}
}
