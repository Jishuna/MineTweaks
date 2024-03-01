package me.jishuna.minetweaks.command;

import me.jishuna.jishlib.command.CommandNode;
import me.jishuna.jishlib.command.RootNode;
import me.jishuna.minetweaks.MineTweaks;

public class MineTweaksCommandHandler extends RootNode {

    public MineTweaksCommandHandler(MineTweaks plugin) {
        super("minetweaks.command");

        CommandNode listCommand = new ListTweaksCommand();
        setDefaultNode(listCommand);

        addChildNode("reload", new ReloadCommand());
        addChildNode("giveitem", new GiveItemCommand());
        addChildNode("list", listCommand);
        addChildNode("toggle", new ToggleCommand());
        addChildNode("status", new StatusCommand());
    }
}
