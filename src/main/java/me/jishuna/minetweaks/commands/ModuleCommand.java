package me.jishuna.minetweaks.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.module.Submodule;
import me.jishuna.minetweaks.api.module.TweakModule;
import net.md_5.bungee.api.ChatColor;

public class ModuleCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;

	public ModuleCommand(MineTweaks plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission("minetweaks.command.module")) {
			sender.sendMessage(this.plugin.getMessage("no-permission"));
			return true;
		}

		if (args.length < 2) {
			List<String> modules = this.plugin.getModuleManager().getModules().stream().map(TweakModule::getName)
					.collect(Collectors.toList());
			modules.add("all");
			sendUsage(sender, "none", modules);
			return true;
		}

		String name = args[0];
		boolean found = false;
		for (TweakModule module : plugin.getModuleManager().getModules()) {
			if (!module.getName().equalsIgnoreCase(name) && !name.equalsIgnoreCase("all"))
				continue;

			boolean enabled = module.isEnabled();
			found = true;

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

		if (!found) {
			List<String> modules = this.plugin.getModuleManager().getModules().stream().map(TweakModule::getName)
					.collect(Collectors.toList());
			modules.add("all");
			sendUsage(sender, name, modules);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 2) {
			List<String> modules = this.plugin.getModuleManager().getModules().stream().map(TweakModule::getName)
					.collect(Collectors.toList());
			modules.add("all");
			List<String> suggestions = new ArrayList<>();

			StringUtil.copyPartialMatches(args[0], modules, suggestions);

			return suggestions;
		}
		return Collections.emptyList();

	}

	private void sendUsage(CommandSender sender, String arg, List<String> args) {
		String msg = this.plugin.getMessage("command-usage");
		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", args));

		sender.sendMessage(msg);
	}

	private ChatColor getBooleanColor(boolean bool) {
		return bool ? ChatColor.GREEN : ChatColor.RED;
	}

}
