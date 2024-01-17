package me.jishuna.minetweaks.tweak.item;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityResurrectEvent;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class TotemCooldownTweak extends Tweak {

    @ConfigEntry("cooldown")
    @Comment("The cooldown (in seconds) on totems of undying.")
    private int cooldown = 5;

    public TotemCooldownTweak() {
        super("totem-cooldown", Category.ITEM);
        this.description = List
                .of(ChatColor.GRAY + "Adds a cooldown to totems of undying, preventing them from being used in quick succession.", "",
                        ChatColor.GRAY + "Cooldown: %cooldown% seconds.");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%cooldown%", ChatColor.GREEN.toString() + this.cooldown);
    }

    @EventHandler(ignoreCancelled = true)
    private void onRessurect(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.getCooldown(Material.TOTEM_OF_UNDYING) > 0) {
            event.setCancelled(true);
        } else {
            player.setCooldown(Material.TOTEM_OF_UNDYING, this.cooldown * 20);
        }
    }
}
