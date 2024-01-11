package me.jishuna.minetweaks.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.minetweaks.inventory.TweakListInventory;

public class ListTweaksCommand extends SimpleCommandHandler {

    protected ListTweaksCommand() {
        super("minetweaks.command.list");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        new TweakListInventory().open(player);
        return true;
    }
}
