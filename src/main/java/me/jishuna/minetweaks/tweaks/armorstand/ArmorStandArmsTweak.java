package me.jishuna.minetweaks.tweaks.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "armorstand_arms")
public class ArmorStandArmsTweak extends Tweak {

	public ArmorStandArmsTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(CreatureSpawnEvent.class, this::onEntitySpawn);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Armor Stands/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getEntity()instanceof ArmorStand stand) {
			stand.setArms(true);
		}
	}

}
