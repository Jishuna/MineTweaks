package me.jishuna.minetweaks.tweaks.mobs;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.TickingTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

@RegisterTweak("horse_stats")
public class HorseStatsTweak extends Tweak implements TickingTweak {
	private static final DecimalFormat FORMATTER = new DecimalFormat("#0.00");
	private String format;
	private boolean requireTamed;

	public HorseStatsTweak(MineTweaks plugin, String name) {
		super(plugin, name);
	}
	
	@Override
	public boolean isToggleable() {
		return true;
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), "Tweaks/Mobs/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.format = ChatColor.translateAlternateColorCodes('&', config.getString("display-format"));
			this.requireTamed = config.getBoolean("require-tamed", true);
		});
	}

	@Override
	public void tick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (isDisabled(player) ||  !(player.getVehicle()instanceof AbstractHorse horse))
				continue;

			if (!horse.isTamed() && requireTamed)
				continue;

			double jump = horse.getJumpStrength();
			double jumpPercent = (jump - 0.4) / 0.6;

			double speed = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
			double speedPercent = (speed - 0.1125) / 0.225;

			double health = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			double healthPercent = (health - 15) / 15d;

			String text = this.format
					.replace("%health%", (int) health + " (" + Math.round(healthPercent * 100) + "%)")
					.replace("%speed%", FORMATTER.format(speed * 10) + " (" + Math.round(speedPercent * 100) + "%)")
					.replace("%jump%", FORMATTER.format(jump * 10) + " (" + Math.round(jumpPercent * 100) + "%)");

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
		}
	}
}
