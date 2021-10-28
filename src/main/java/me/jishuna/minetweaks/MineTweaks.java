package me.jishuna.minetweaks;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.commonlib.language.MessageConfig;
import me.jishuna.commonlib.utils.FileUtils;
import me.jishuna.commonlib.utils.ServerUtils;
import me.jishuna.minetweaks.api.events.EventManager;
import me.jishuna.minetweaks.api.module.ModuleManager;
import me.jishuna.minetweaks.commands.MineTweaksCommandHandler;
import me.jishuna.minetweaks.modules.ArmorstandModule;
import me.jishuna.minetweaks.modules.BlockModule;
import me.jishuna.minetweaks.modules.DispenserModule;
import me.jishuna.minetweaks.modules.FarmingModule;
import me.jishuna.minetweaks.modules.ItemModule;
import me.jishuna.minetweaks.modules.ItemframeModule;
import me.jishuna.minetweaks.modules.MiscModule;
import me.jishuna.minetweaks.modules.MobModule;
import me.jishuna.minetweaks.modules.RecipeModule;
import me.jishuna.minetweaks.modules.ScoreboardModule;
import me.jishuna.minetweaks.modules.WanderingTraderModule;

public class MineTweaks extends JavaPlugin {

	private static final int BSTATS_ID = 13045;
	private ModuleManager moduleManager = new ModuleManager();
	private EventManager eventManager;
	private MessageConfig messageConfig;

	@Override
	public void onEnable() {
		registerBaseModules();
		loadConfiguration();

		this.eventManager = new EventManager(this);

		getCommand("minetweaks").setExecutor(new MineTweaksCommandHandler(this));

		initializeMetrics();
	}

	private void registerBaseModules() {
		this.moduleManager.registerModule(new ArmorstandModule(this));
		this.moduleManager.registerModule(new ItemframeModule(this));
		this.moduleManager.registerModule(new FarmingModule(this));
		this.moduleManager.registerModule(new DispenserModule(this));
		this.moduleManager.registerModule(new BlockModule(this));
		this.moduleManager.registerModule(new ItemModule(this));
		this.moduleManager.registerModule(new MiscModule(this));
		this.moduleManager.registerModule(new MobModule(this));
		this.moduleManager.registerModule(new RecipeModule(this));
		this.moduleManager.registerModule(new ScoreboardModule(this));
		this.moduleManager.registerModule(new WanderingTraderModule(this));
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
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
