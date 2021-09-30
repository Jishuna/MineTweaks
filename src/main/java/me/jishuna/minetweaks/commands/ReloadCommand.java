package me.jishuna.minetweaks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

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

		sender.sendMessage(ChatColor.GREEN + "Reloading Messages File.");
		this.plugin.getMessageConfig().refresh();

		sender.sendMessage(ChatColor.GREEN + "Reloading Modules.");
		this.plugin.getModuleManager().getModules().forEach(TweakModule::reload);

		sender.sendMessage(ChatColor.GREEN + "Reload Complete!");
		return true;
	}

}
