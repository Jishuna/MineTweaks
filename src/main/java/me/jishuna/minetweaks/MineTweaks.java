package me.jishuna.minetweaks;

import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.minetweaks.api.events.EventManager;
import me.jishuna.minetweaks.api.module.ModuleManager;
import me.jishuna.minetweaks.modules.ArmorstandModule;
import me.jishuna.minetweaks.modules.BonemealModule;
import me.jishuna.minetweaks.modules.DispenserModule;
import me.jishuna.minetweaks.modules.ItemframeModule;
import me.jishuna.minetweaks.modules.MiscModule;
import me.jishuna.minetweaks.modules.MobModule;
import me.jishuna.minetweaks.modules.RecipeModule;
import me.jishuna.minetweaks.modules.ScoreboardModule;
import me.jishuna.minetweaks.modules.WanderingTraderModule;

public class MineTweaks extends JavaPlugin {
	
	private ModuleManager moduleManager = new ModuleManager();
	private EventManager eventManager;

	@Override
	public void onEnable() {
		registerBaseModules();
		this.eventManager = new EventManager(this);
	}

	private void registerBaseModules() {
		this.moduleManager.registerModule(new ArmorstandModule(this));
		this.moduleManager.registerModule(new ItemframeModule(this));
		this.moduleManager.registerModule(new BonemealModule(this));
		this.moduleManager.registerModule(new DispenserModule(this));
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

}
