package me.jishuna.minetweaks.command;

import me.jishuna.jishlib.command.ArgumentCommandHandler;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.minetweaks.MineTweaks;

public class MineTweaksCommandHandler extends ArgumentCommandHandler {

    public MineTweaksCommandHandler(MineTweaks plugin) {
        super("minetweaks.command", () -> "", () -> "");

        SimpleCommandHandler listCommand = new ListTweaksCommand();
        setDefault(listCommand);

        addArgumentExecutor("reload", new ReloadCommand());
        addArgumentExecutor("giveitem", new GiveItemCommand());
        addArgumentExecutor("list", listCommand);
        addArgumentExecutor("toggle", new ToggleCommand());
        addArgumentExecutor("status", new StatusCommand());
    }
}
