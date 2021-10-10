package me.jishuna.minetweaks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.module.Submodule;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class InfoCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;

	public InfoCommand(MineTweaks plugin) {
		super("minetweaks.command.info");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessage("no-permission"));
			return true;
		}
		PluginDescriptionFile descriptionFile = this.plugin.getDescription();
		sender.sendMessage(ChatColor.GOLD + "=".repeat(40));
		sender.sendMessage(ChatColor.GOLD + descriptionFile.getFullName() + ChatColor.GREEN + " by " + ChatColor.GOLD
				+ String.join(", ", descriptionFile.getAuthors()));
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.GOLD + "Enabled Modules: " + ChatColor.GREEN + getEnabledModules());
		sender.sendMessage(ChatColor.GOLD + "Enabled Sub Modules: " + ChatColor.GREEN + getEnabledSubModules());
		sender.sendMessage(ChatColor.GOLD + "=".repeat(40));

		return true;
	}

	private String getEnabledModules() {
		int total = plugin.getModuleManager().getModules().size();
		long enabled = plugin.getModuleManager().getModules().stream().filter(module -> module.isEnabled()).count();
		return enabled + "/" + total;
	}

	private String getEnabledSubModules() {
		int total = 0;
		int enabled = 0;

		for (TweakModule module : this.plugin.getModuleManager().getModules()) {
			for (Submodule subModule : module.getSubModules()) {
				total++;
				if (module.getBoolean(subModule.getKey(), false))
					enabled++;
			}
		}
		return enabled + "/" + total;
	}

}
