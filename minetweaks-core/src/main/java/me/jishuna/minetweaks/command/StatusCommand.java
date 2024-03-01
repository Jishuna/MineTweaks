package me.jishuna.minetweaks.command;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.command.LeafNode;
import me.jishuna.jishlib.command.argument.ArgumentQueue;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

public class StatusCommand extends LeafNode {

    protected StatusCommand() {
        super("minetweaks.command.status");
    }

    @Override
    public void handleCommand(CommandSender sender, ArgumentQueue args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        sendStatus(player);
    }

    public static void sendStatus(Player player) {
        player.sendMessage(MessageAPI.get("tweak.status.prefix"));
        for (ToggleableTweak tweak : Registries.TWEAK.getToggleableTweaks()) {
            String state = tweak.isEnabled(player) ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");

            BaseComponent component = StringUtils.combineComponents(TextComponent.fromLegacyText(MessageAPI.getLegacy("tweak.status", ((Tweak) tweak).getDisplayName(), state)));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(MessageAPI.get("tweak.toggle"))));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mt toggle " + ((Tweak) tweak).getName()));

            player.spigot().sendMessage(component);
        }
        player.sendMessage(MessageAPI.get("tweak.status.suffix"));
    }
}
