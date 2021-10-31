package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "disable_enderman_griefing")
public class EndermanGriefingTweak extends Tweak {

	public EndermanGriefingTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityChangeBlockEvent.class, this::onBlockChange);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onBlockChange(EntityChangeBlockEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN) {
			event.setCancelled(true);
		}
	}
}
