package me.jishuna.minetweaks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.module.TweakModule;

public class ReloadCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;

	public ReloadCommand(MineTweaks plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission("minetweaks.command.reload")) {
			sender.sendMessage(this.plugin.getMessage("no-permission"));
			return true;
		}

		sender.sendMessage(this.plugin.getMessage("reload-messages"));
		this.plugin.getMessageConfig().refresh();

		sender.sendMessage(this.plugin.getMessage("reload-modules"));
		this.plugin.getModuleManager().getModules().forEach(TweakModule::reload);

		sender.sendMessage(this.plugin.getMessage("reload-complete"));
		return true;
	}

}
