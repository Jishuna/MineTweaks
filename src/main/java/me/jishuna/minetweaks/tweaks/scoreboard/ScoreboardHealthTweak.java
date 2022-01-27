package me.jishuna.minetweaks.tweaks.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.minetweaks.api.RegisterTweak;
import me.jishuna.minetweaks.api.tweak.CleanupTweak;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;

@RegisterTweak(name = "scoreboard_health")
public class ScoreboardHealthTweak extends Tweak implements CleanupTweak {
	private String display;
	private String type;

	public ScoreboardHealthTweak(JavaPlugin plugin, String name) {
		super(plugin, name);

		addEventHandler(PlayerJoinEvent.class, this::onJoin);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getOwningPlugin(), "Tweaks/Scoreboard/" + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
			this.cleanup();

			this.display = ChatColor.translateAlternateColorCodes('&', config.getString("health-display", "Health"));
			this.type = config.getString("health-display-type", "PLAYER_LIST").toUpperCase();
		});

	}

	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Objective objective = player.getScoreboard().getObjective("mt_player_health");
		if (objective == null) {
			objective = player.getScoreboard().registerNewObjective("mt_player_health", Criterias.HEALTH, "temp");
		}

		objective.setDisplayName(display);

		try {
			objective.setDisplaySlot(DisplaySlot.valueOf(type));
		} catch (IllegalArgumentException ex) {
			objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		}
	}

	@Override
	public void cleanup() {
		Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("mt_player_health");
		if (objective != null) {
			objective.unregister();
		}
	}

}
