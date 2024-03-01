package me.jishuna.minetweaks.command;

import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.command.LeafNode;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.Registries;

public class ReloadCommand extends LeafNode {

    protected ReloadCommand() {
        super("minetweaks.command.reload");
    }

    @Override
    public void handleCommand(CommandSender sender, ArgumentQueue args) {
        MessageAPI.reload();
        Registries.TWEAK.reload();

        sender.sendMessage(MessageAPI.get("command.reload.success"));
    }
}
