package me.jishuna.minetweaks.tweaks.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("armorstand_arms")
public class ArmorStandArmsTweak extends Tweak {

	public ArmorStandArmsTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(CreatureSpawnEvent.class, EventPriority.HIGH, this::onEntitySpawn);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Armor Stands/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getEntity()instanceof ArmorStand stand) {
			stand.setArms(true);
		}
	}

}
