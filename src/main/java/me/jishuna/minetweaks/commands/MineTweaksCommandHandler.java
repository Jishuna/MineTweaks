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
		addArgumentExecutor("tweaks", new TweaksCommand(plugin));
		addArgumentExecutor("toggle", new ToggleCommand(plugin));
		addArgumentExecutor("status", new StatusCommand(plugin));
		addArgumentExecutor("reload", new ReloadCommand(plugin));
	}
}