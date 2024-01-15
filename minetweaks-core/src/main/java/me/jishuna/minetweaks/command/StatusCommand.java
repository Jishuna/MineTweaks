package me.jishuna.minetweaks.command;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.command.SimpleCommandHandler;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

public class StatusCommand extends SimpleCommandHandler {

    protected StatusCommand() {
        super("minetweaks.command.status");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        sendStatus(player);
        return true;
    }

    public static void sendStatus(Player player) {
        player.sendMessage(MessageAPI.get("tweak.status.prefix"));
        for (ToggleableTweak tweak : Registries.TWEAK.getToggleableTweaks()) {
            String state = tweak.isEnabled(player) ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");

            BaseComponent component = combineComponents(TextComponent.fromLegacyText(MessageAPI.get("tweak.status", ((Tweak) tweak).getDisplayName(), state)));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(MessageAPI.get("tweak.toggle"))));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mt toggle " + ((Tweak) tweak).getName()));

            player.spigot().sendMessage(component);
        }
        player.sendMessage(MessageAPI.get("tweak.status.suffix"));
    }

    private static BaseComponent combineComponents(BaseComponent[] components) {
        BaseComponent parent = components[0];
        if (components.length == 1) {
            return parent;
        }

        for (int i = 1; i < components.length; i++) {
            parent.addExtra(components[i]);
        }

        return parent;
    }
}
