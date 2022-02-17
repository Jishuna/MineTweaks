package me.jishuna.minetweaks.tweaks.mobs;

import java.util.EnumSet;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.jishuna.commonlib.Version;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("1_18_mob_spawning")
public class NewSpawningTweak extends Tweak {
	EnumSet<EntityType> monsters = EnumSet.of(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.ENDERMAN,
			EntityType.CREEPER, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.DROWNED,
			EntityType.ZOMBIE_VILLAGER);

	public NewSpawningTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(CreatureSpawnEvent.class, EventPriority.HIGH, this::onSpawn);
		
		addInvalidVersions(Version.V1_18_R1);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, false);
		});
	}

	private void onSpawn(CreatureSpawnEvent event) {
		if (monsters.contains(event.getEntityType())
				&& event.getSpawnReason() == SpawnReason.NATURAL
				&& event.getEntity().getLocation().getBlock().getLightFromBlocks() > 0) {
			event.setCancelled(true);
		}
	}
}
