package me.jishuna.minetweaks.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardModule extends TweakModule {

	public ScoreboardModule(JavaPlugin plugin) {
		super(plugin, "scoreboard");

		addSubModule("show-health-scoreboard");
		
		addEventHandler(PlayerJoinEvent.class, this::onJoin);
	}

	private void onJoin(PlayerJoinEvent event) {
		if (getBoolean("show-health-scoreboard", true)) {
			Player player = event.getPlayer();

			Objective objective = player.getScoreboard().getObjective("player_health");
			if (objective == null) {
				objective = player.getScoreboard().registerNewObjective("player_health", Criterias.HEALTH,
						"temp");
			}
			
			objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', getString("health-display", "Health")));

			try {
				objective.setDisplaySlot(
						DisplaySlot.valueOf(getString("health-display-type", "PLAYER_LIST").toUpperCase()));
			} catch (IllegalArgumentException ex) {
				objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			}
		}
	}

}
