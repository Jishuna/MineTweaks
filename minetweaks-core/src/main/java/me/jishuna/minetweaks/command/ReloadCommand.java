package me.jishuna.minetweaks.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.minetweaks.Registries;

public class ReloadCommand extends SimpleCommandHandler {

    protected ReloadCommand() {
        super("minetweaks.command.reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Registries.TWEAKS.reload();
        return true;
    }
}
