package me.jishuna.minetweaks.tweaks.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;

@RegisterTweak("pet_protection")
public class PetProtectionTweak extends Tweak {

	public PetProtectionTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(EntityDamageByEntityEvent.class, EventPriority.HIGH, this::onDamage);
	}
	
	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	private void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Tameable tamable) || tamable.getOwner() == null)
			return;

		Entity damager = event.getDamager();
		Player player = null;

		if (damager instanceof Player) {
			player = (Player) damager;
		} else if (damager instanceof Projectile projecitle) {
			ProjectileSource shooter = projecitle.getShooter();

			if (shooter instanceof Player) {
				player = (Player) shooter;
			}
		}

		if (player == null || isDisabled(player))
			return;

		if (tamable.getOwner().getUniqueId().equals(player.getUniqueId()))
			event.setCancelled(true);
	}
}
