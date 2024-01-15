package me.jishuna.minetweaks.tweak.mob;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.TickingTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class HorseStatsTweak extends Tweak implements TickingTweak {
    private static final DecimalFormat FORMAT = new DecimalFormat("##.#");

    @ConfigEntry("require-tamed")
    @Comment("Whether only tamed horses should display their stats")
    private boolean requireTamed = true;

    @ConfigEntry("message")
    @Comment("Allows changing the format of the message sent to players")
    private String message = StringUtils.miniMessageToLegacy("<gold>Health: <green>{0} <dark_green>({1}%)   <gold>Speed: <green>{2} <dark_green>({3}%)   <gold>Jump: <green>{4} <dark_green>({5}%)");

    public HorseStatsTweak() {
        super("horse-stat-display", Category.MOB);
        this.description = List.of(ChatColor.GRAY + "Shows information about the speed, jump height, and health of the horse you are riding.");
    }

    @Override
    public void tick() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!(player.getVehicle() instanceof AbstractHorse horse) || (!horse.isTamed() && this.requireTamed)) {
                continue;
            }

            double health = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            String healthPercent = FORMAT.format((health - 15) / 15d * 100);

            double speed = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
            String speedPercent = FORMAT.format((speed - 0.1125) / 0.225 * 100);

            double jump = horse.getJumpStrength();
            String jumpPercent = FORMAT.format((jump - 0.4) / 0.6 * 100);

            BaseComponent[] component = TextComponent.fromLegacyText(MessageFormat.format(this.message, health, healthPercent, speed, speedPercent, jump, jumpPercent));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }
}
