package me.jishuna.minetweaks.tweaks.trader;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;

@RegisterTweak("announce_spawn")
public class TraderSpawnMessageTweak extends Tweak {
	private String message;

	public TraderSpawnMessageTweak(MineTweaks plugin, String name) {
		super(plugin, name);

		addEventHandler(CreatureSpawnEvent.class, EventPriority.HIGH, this::onSpawn);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Wandering Trader/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.message = ChatColor.translateAlternateColorCodes('&',
					config.getString("announce-message", "Placeholder"));
		});

	}

	private void onSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled())
			return;
		
		if (event.getEntity()instanceof WanderingTrader trader) {
			Location location = event.getEntity().getLocation();
			String locationString = "X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: "
					+ location.getBlockZ();

			String msg = this.message.replace("%location%", locationString).replace("%world%",
					location.getWorld().getName());
			Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(msg));
		}
	}
}
