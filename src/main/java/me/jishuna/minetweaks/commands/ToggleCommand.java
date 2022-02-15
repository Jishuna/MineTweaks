package me.jishuna.minetweaks.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.StringUtil;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;

public class ToggleCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;
	private List<String> tweaks = null;

	public ToggleCommand(MineTweaks plugin) {
		super("minetweaks.command.toggle");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessage("no-permission"));
			return true;
		}

		if (!(sender instanceof Player player)) {
			return true;
		}

		if (args.length < 2) {
			sendUsage(sender, "none");
			return true;
		}

		String name = args[0].toLowerCase();
		Tweak tweak = plugin.getTweakManager().getTweak(name);

		if (tweak == null || !tweak.isToggleable()) {
			sendUsage(sender, name);
			return true;
		}

		PersistentDataContainer container = player.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(this.plugin, "toggle-" + name);

		if (container.has(key, PersistentDataType.BYTE)) {
			container.remove(key);
			sender.sendMessage(plugin.getMessage("toggle-enabled").replace("%tweak%", tweak.getDisplayName()));
		} else {
			container.set(key, PersistentDataType.BYTE, (byte) 1);
			sender.sendMessage(plugin.getMessage("toggle-disabled").replace("%tweak%", tweak.getDisplayName()));
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 2) {
			if (this.tweaks == null) {
				this.tweaks = plugin.getTweakManager().getTweaks().stream().filter(Tweak::isToggleable)
						.map(Tweak::getName).toList();
			}

			return StringUtil.copyPartialMatches(args[0], this.tweaks, new ArrayList<>());
		}
		return Collections.emptyList();
	}

	private void sendUsage(CommandSender sender, String arg) {
		String msg = this.plugin.getMessage("command-usage");
		
		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", this.tweaks));

		sender.sendMessage(msg);
	}

}
