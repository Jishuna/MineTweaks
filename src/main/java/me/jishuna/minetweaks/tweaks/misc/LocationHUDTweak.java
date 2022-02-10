package me.jishuna.minetweaks.tweaks.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.TickingTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@RegisterTweak(name = "location_hud")
public class LocationHUDTweak extends Tweak implements TickingTweak {
	private String format;

	public LocationHUDTweak(JavaPlugin plugin, String name) {
		super(plugin, name);
	}
	
	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Misc/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.format = ChatColor.translateAlternateColorCodes('&', config.getString("display-format"));
		});
	}

	@Override
	public void tick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (isDisabled(player) || player.getVehicle() instanceof AbstractHorse horse)
				continue;
			
			Location location = player.getLocation();

			String text = this.format
					.replace("%x%", Integer.toString(location.getBlockX()))
					.replace("%y%", Integer.toString(location.getBlockY()))
					.replace("%z%", Integer.toString(location.getBlockZ()))
					.replace("%dir%",player.getFacing().toString().substring(0, 1));

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
		}
	}
}
