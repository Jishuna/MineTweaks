package me.jishuna.minetweaks.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.LeafNode;
import me.jishuna.jishlib.command.argument.ArgumentParsers;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.jishlib.util.InventoryUtils;
import me.jishuna.minetweaks.Registries;

public class GiveItemCommand extends LeafNode {

    protected GiveItemCommand() {
        super("minetweaks.command.giveitem");
    }

    @Override
    public void handleCommand(CommandSender sender, ArgumentQueue args) {
        NamespacedKey key = args.pollAs(NamespacedKey.class);
        ItemStack item = Registries.ITEM.get(key);

        if (item == null) {
            sender.sendMessage(MessageAPI.getLegacy("command.invalid-arg", key.toString(), String.join(", ", Registries.ITEM.getKeys().stream().map(Object::toString).toList())));
            return;
        }

        Player target;
        if (!args.isEmpty()) {
            target = args.pollAs(Player.class);
        } else if (sender instanceof Player player) {
            target = player;
        } else {
            sender.sendMessage(MessageAPI.get("command.giveitem.player-only"));
            return;
        }

        if (target == null) {
            return;
        }

        InventoryUtils.addOrDropItem(target.getInventory(), target::getLocation, item);
        sender.sendMessage(MessageAPI.getLegacy("command.giveitem.success", key, target.getName()));
    }

    @Override
    protected List<String> handleTabComplete(CommandSender sender, ArgumentQueue args) {
        if (args.size() == 1) {
            return StringUtil.copyPartialMatches(args.poll(), Registries.ITEM.getKeys().stream().map(Object::toString).toList(), new ArrayList<>());
        }

        if (args.size() == 2) {
            return ArgumentParsers.PLAYER.getSuggestions(args.pollLast());
        }
        return Collections.emptyList();
    }
}
