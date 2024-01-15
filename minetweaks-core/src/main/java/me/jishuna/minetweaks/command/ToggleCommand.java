package me.jishuna.minetweaks.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

public class ToggleCommand extends SimpleCommandHandler {

    protected ToggleCommand() {
        super("minetweaks.command.toggle");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        Tweak tweak = Registries.TWEAK.get(args[0]);
        if (tweak == null || !(tweak instanceof ToggleableTweak toggleable)) {
            return true;
        }

        toggleable.toggle(player);
        StatusCommand.sendStatus(player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> toggleable = Registries.TWEAK.getToggleableTweaks().stream().map(Tweak.class::cast).map(Tweak::getName).toList();
            return StringUtil.copyPartialMatches(args[0], toggleable, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}
