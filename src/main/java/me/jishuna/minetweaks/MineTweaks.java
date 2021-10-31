package me.jishuna.minetweaks;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.language.MessageConfig;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.ServerUtils;
import me.jishuna.minetweaks.api.events.EventManager;
import me.jishuna.minetweaks.api.tweak.TweakManager;
import me.jishuna.minetweaks.commands.MineTweaksCommandHandler;

public class MineTweaks extends JavaPlugin {

	private static final int BSTATS_ID = 13045;
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
		metrics.addCustomChart(new SimplePie("online_status", () -> ServerUtils.getOnlineMode()));
	}
}
