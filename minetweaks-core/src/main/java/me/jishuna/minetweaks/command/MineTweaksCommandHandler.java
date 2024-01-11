package me.jishuna.minetweaks.command;

import me.jishuna.jishlib.command.ArgumentCommandHandler;
import me.jishuna.minetweaks.MineTweaks;

public class MineTweaksCommandHandler extends ArgumentCommandHandler {

    public MineTweaksCommandHandler(MineTweaks plugin) {
        super("minetweaks.command", () -> "", () -> "");

        addArgumentExecutor("reload", new ReloadCommand());
        addArgumentExecutor("giveitem", new GiveItemCommand());
        addArgumentExecutor("list", new ListTweaksCommand());
    }
}
