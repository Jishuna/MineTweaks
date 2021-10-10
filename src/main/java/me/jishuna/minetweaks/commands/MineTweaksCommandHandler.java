package me.jishuna.minetweaks.commands;

import me.jishuna.commonlib.commands.ArgumentCommandHandler;
import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;

public class MineTweaksCommandHandler extends ArgumentCommandHandler {
	public MineTweaksCommandHandler(MineTweaks plugin) {
		super(plugin.getMessageConfig(), "minetweaks.command");

		SimpleCommandHandler info = new InfoCommand(plugin);

		setDefault(info);
		addArgumentExecutor("info", info);
		addArgumentExecutor("module", new ModuleCommand(plugin));
		addArgumentExecutor("reload", new ReloadCommand(plugin));
	}
}