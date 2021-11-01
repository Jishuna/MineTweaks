package me.jishuna.minetweaks;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.SimpleSemVersion;
import me.jishuna.commonlib.UpdateChecker;
import me.jishuna.commonlib.language.MessageConfig;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.ServerUtils;
import me.jishuna.minetweaks.api.events.EventManager;
import me.jishuna.minetweaks.api.tweak.TweakManager;
import me.jishuna.minetweaks.commands.MineTweaksCommandHandler;
import net.md_5.bungee.api.ChatColor;

public class MineTweaks extends JavaPlugin {

	private static final int BSTATS_ID = 13045;
	private static int PLUGIN_ID = 96757;

	private TweakManager tweakManager;
	private EventManager eventManager;
	private MessageConfig messageConfig;

	@Override
	public void onEnable() {
		loadConfiguration();

		this.tweakManager = new TweakManager(this);
		this.eventManager = new EventManager(this);

		getCommand("minetweaks").setExecutor(new MineTweaksCommandHandler(this));

		initializeMetrics();
		initializeUpdateChecker();
	}

	public TweakManager getTweakManager() {
		return tweakManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public MessageConfig getMessageConfig() {
		return messageConfig;
	}

	public void loadConfiguration() {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();

		FileUtils.loadResourceFile(this, "messages.yml")
				.ifPresent(file -> this.messageConfig = new MessageConfig(file));
	}

	public String getMessage(String key) {
		return this.messageConfig.getString(key);
	}

	private void initializeMetrics() {
		Metrics metrics = new Metrics(this, BSTATS_ID);
		metrics.addCustomChart(new SimplePie("online_status", ServerUtils::getOnlineMode));
	}

	private void initializeUpdateChecker() {
		UpdateChecker checker = new UpdateChecker(this, PLUGIN_ID);
		SimpleSemVersion current = SimpleSemVersion.fromString(this.getDescription().getVersion());

		Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> checker.getVersion(version -> {
			if (SimpleSemVersion.fromString(version).isNewerThan(current)) {
				ConsoleCommandSender sender = Bukkit.getConsoleSender();
				sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
				sender.sendMessage(ChatColor.GOLD + "A new version of MineTweaks is available: " + ChatColor.DARK_AQUA + version);
				sender.sendMessage(
						ChatColor.GOLD + "Download it at https://www.spigotmc.org/resources/minetweaks.96757/");
				sender.sendMessage(ChatColor.GOLD + "=".repeat(70));
			}
		}), 0, 20l * 60 * 60);
	}
}
