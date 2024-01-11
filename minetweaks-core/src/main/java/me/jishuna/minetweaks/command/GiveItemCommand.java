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
import me.jishuna.minetweaks.Registries;

public class GiveItemCommand extends SimpleCommandHandler {

    protected GiveItemCommand() {
        super("minetweaks.command.giveitem");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("args");
            return true;
        }

        NamespacedKey key = NamespacedKey.fromString(args[0]);
        ItemStack item = Registries.ITEM.get(key);

        if (item == null) {
            sender.sendMessage("item");
            return true;
        }

        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
        } else if (sender instanceof Player player) {
            target = player;
        } else {
            sender.sendMessage("must be player");
            return true;
        }

        if (target == null) {
            sender.sendMessage("no player");
            return true;
        }

        target.getInventory().addItem(item);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Registries.ITEM.getKeys().stream().map(Object::toString).toList(), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
