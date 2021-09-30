package me.jishuna.minetweaks.commands;

import me.jishuna.commonlib.commands.ArgumentCommandHandler;
import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;

public class MineTweaksCommandHandler extends ArgumentCommandHandler {
	public MineTweaksCommandHandler(MineTweaks plugin) {
		super(plugin.getMessageConfig());

		SimpleCommandHandler temp = new ModuleCommand(plugin);

		setDefault(temp);
		addArgumentExecutor("module", temp);
		addArgumentExecutor("reload", new ReloadCommand(plugin));
	}
}
