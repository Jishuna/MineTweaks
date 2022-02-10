package me.jishuna.minetweaks.tweaks.misc;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak(name = "blood_effect")
public class BloodEffectTweak extends Tweak {
	private BlockData data;
	private int count;

	public BloodEffectTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityDamageByEntityEvent.class, this::onDamage);
	}

	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.count = config.getInt("particle-count", 10);

			Material material = Material.getMaterial(config.getString("particle-material").toUpperCase());
			this.data = material == null ? Material.RED_CONCRETE.createBlockData() : material.createBlockData();
		});
	}

	private void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity living) || !(event.getDamager() instanceof Player player) || isDisabled(player))
			return;

		double halfHeight = living.getHeight() / 2;
		double width = living.getWidth() / 4;

		player.spawnParticle(Particle.BLOCK_DUST, living.getLocation().add(0, halfHeight, 0), this.count, width,
				halfHeight / 2, width, 0, this.data);
	}
}
