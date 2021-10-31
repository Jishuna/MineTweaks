package me.jishuna.minetweaks.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;
import net.md_5.bungee.api.ChatColor;

public class TweaksCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;

	public TweaksCommand(MineTweaks plugin) {
		super("minetweaks.command.tweaks");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessage("no-permission"));
			return true;
		}

		if (args.length < 2) {
			sendUsage(sender, "none");
			return true;
		}

		String name = args[0].replace("_", " ");
		boolean found = false;
		for (Tweak tweak : plugin.getTweakManager().getTweaks()) {
			if (!tweak.getCategory().equalsIgnoreCase(name) && !name.equalsIgnoreCase("all"))
				continue;

			boolean enabled = tweak.isEnabled();
			found = true;

			sender.sendMessage(getBooleanColor(enabled).toString() + ChatColor.BOLD + tweak.getDisplayName() + ": "
					+ ChatColor.GRAY + tweak.getDescription());
			sender.sendMessage("");
		}

		if (!found) {
			sendUsage(sender, name);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 2) {
			Set<String> modules = this.plugin.getTweakManager().getCategories();
			modules.add("all");

			return StringUtil.copyPartialMatches(args[0], modules,  new ArrayList<>());
		}
		return Collections.emptyList();

	}

	private void sendUsage(CommandSender sender, String arg) {
		Set<String> modules = this.plugin.getTweakManager().getCategories();
		modules.add("all");

		String msg = this.plugin.getMessage("command-usage");
		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", modules));

		sender.sendMessage(msg);
	}

	private ChatColor getBooleanColor(boolean bool) {
		return bool ? ChatColor.GREEN : ChatColor.RED;
	}

}
