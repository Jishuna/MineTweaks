package me.jishuna.minetweaks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.module.Submodule;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class TempCommand implements CommandExecutor {

	private final MineTweaks plugin;

	public TempCommand(MineTweaks plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		for (TweakModule module : plugin.getModuleManager().getModules()) {
			boolean enabled = module.isEnabled();

			sender.sendMessage(getBooleanColor(enabled).toString() + ChatColor.BOLD + module.getFriendlyName() + ":");

			if (enabled) {
				for (Submodule submodule : module.getSubModules()) {
					boolean subEnabled = module.getBoolean(submodule.getKey(), false);
					sender.sendMessage(ChatColor.GRAY + " - " + getBooleanColor(subEnabled) + submodule.getName() + ": "
							+ ChatColor.GRAY + submodule.getDescription());
				}
			}
			sender.sendMessage("");
		}
		return true;
	}

	private ChatColor getBooleanColor(boolean bool) {
		return bool ? ChatColor.GREEN : ChatColor.RED;
	}

}
