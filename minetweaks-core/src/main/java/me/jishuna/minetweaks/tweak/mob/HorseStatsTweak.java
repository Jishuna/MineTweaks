package me.jishuna.minetweaks.tweak.mob;

import java.text.DecimalFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.TickingTweak;

public class HorseStatsTweak extends TickingTweak {
    private static final DecimalFormat FORMAT = new DecimalFormat("##.#");

    @ConfigEntry("require-tamed")
    @Comment("Whether only tamed horses should display their stats")
    private final boolean requireTamed = true;

    public HorseStatsTweak() {
        this.name = "horse-stats";
        this.category = Category.MOB;
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

            String message = MessageAPI.get("horse-stats.message", health, healthPercent, speed, speedPercent, jump, jumpPercent);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }
}
