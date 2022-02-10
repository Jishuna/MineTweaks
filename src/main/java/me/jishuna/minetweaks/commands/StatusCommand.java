package me.jishuna.minetweaks.commands;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;
import me.jishuna.minetweaks.api.tweak.Tweak;

public class StatusCommand extends SimpleCommandHandler {

	private final MineTweaks plugin;

	public StatusCommand(MineTweaks plugin) {
		super("minetweaks.command.status");
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

		PersistentDataContainer container = player.getPersistentDataContainer();
		sender.sendMessage(plugin.getMessage("status"));

		for (Tweak tweak : this.plugin.getTweakManager().getTweaks()) {
			if (!tweak.isToggleable())
				continue;

			String name = tweak.getName();
			NamespacedKey key = new NamespacedKey(this.plugin, "toggle-" + name);

			name = StringUtils.capitalize(name.replace("_", " "));
			
			if (container.has(key, PersistentDataType.BYTE)) {
				sender.sendMessage(plugin.getMessage("status-disabled").replace("%tweak%", name));
			} else {
				sender.sendMessage(plugin.getMessage("status-enabled").replace("%tweak%", name));
			}

		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Collections.emptyList();
	}
}
