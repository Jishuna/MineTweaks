package me.jishuna.minetweaks.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.command.LeafNode;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.minetweaks.inventory.TweakListInventory;

public class ListTweaksCommand extends LeafNode {

    protected ListTweaksCommand() {
        super("minetweaks.command.list");
    }

    @Override
    public void handleCommand(CommandSender sender, ArgumentQueue args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        new TweakListInventory().open(player);
    }
}
