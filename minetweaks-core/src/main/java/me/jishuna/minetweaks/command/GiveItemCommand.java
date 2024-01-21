package me.jishuna.minetweaks.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.jishlib.util.InventoryUtils;
import me.jishuna.minetweaks.Registries;

public class GiveItemCommand extends SimpleCommandHandler {

    protected GiveItemCommand() {
        super("minetweaks.command.giveitem");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(MessageAPI.get("command.invalid-arg", "none", String.join(",", Registries.ITEM.getKeys().stream().map(Object::toString).toList())));
            return true;
        }

        NamespacedKey key = NamespacedKey.fromString(args[0]);
        ItemStack item = Registries.ITEM.get(key);

        if (item == null) {
            sender.sendMessage(MessageAPI.get("command.invalid-arg", args[0], String.join(", ", Registries.ITEM.getKeys().stream().map(Object::toString).toList())));
            return true;
        }

        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
        } else if (sender instanceof Player player) {
            target = player;
        } else {
            sender.sendMessage(MessageAPI.get("command.giveitem.player-only"));
            return true;
        }

        if (target == null) {
            sender.sendMessage(MessageAPI.get("command.giveitem.player-not-found", args[1]));
            return true;
        }

        InventoryUtils.addOrDropItem(target.getInventory(), target::getLocation, item);
        sender.sendMessage(MessageAPI.get("command.giveitem.success", key, target.getName()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Registries.ITEM.getKeys().stream().map(Object::toString).toList(), new ArrayList<>());
        }

        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
