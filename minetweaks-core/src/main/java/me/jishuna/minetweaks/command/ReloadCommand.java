package me.jishuna.minetweaks.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.Registries;

public class ReloadCommand extends SimpleCommandHandler {

    protected ReloadCommand() {
        super("minetweaks.command.reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageAPI.reload();
        Registries.TWEAK.reload();
        
        sender.sendMessage(MessageAPI.get("command.reload.success"));
        return true;
    }
}
