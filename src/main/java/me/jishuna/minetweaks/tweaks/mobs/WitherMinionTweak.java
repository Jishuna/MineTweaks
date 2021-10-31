package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.PluginKeys;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "wither_spawns_skeletons")
public class WitherMinionTweak extends Tweak {
	public WitherMinionTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityDamageEvent.class, this::onDamage);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;

		if (event.getEntity()instanceof Wither wither) {
			if (wither.getPersistentDataContainer().has(PluginKeys.SKELETONS_SPAWNED.getKey(), PersistentDataType.BYTE)
					|| wither.getHealth() - event
							.getFinalDamage() > (wither.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2))
				return;

			wither.getPersistentDataContainer().set(PluginKeys.SKELETONS_SPAWNED.getKey(), PersistentDataType.BYTE,
					(byte) 1);

			for (int i = 0; i < 3; i++) {
				wither.getWorld().spawn(wither.getLocation(), WitherSkeleton.class);
			}
		}
	}
}
