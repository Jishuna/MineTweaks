package me.jishuna.minetweaks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;
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
		sender.sendMessage(ChatColor.GOLD + "Enabled Tweaks: " + ChatColor.GREEN + getEnabledModules());
		sender.sendMessage(ChatColor.GOLD + "=".repeat(40));

		return true;
	}

	private String getEnabledModules() {
		int total = plugin.getTweakManager().getTweaks().size();
		long enabled = plugin.getTweakManager().getTweaks().stream().filter(Tweak::isEnabled).count();
		return enabled + "/" + total;
	}

}
