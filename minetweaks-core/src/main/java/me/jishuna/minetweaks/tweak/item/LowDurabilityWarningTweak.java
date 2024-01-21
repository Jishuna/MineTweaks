package me.jishuna.minetweaks.tweak.item;

import java.text.MessageFormat;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class LowDurabilityWarningTweak extends Tweak implements ToggleableTweak {

    @ConfigEntry("threshold")
    @Comment("Items must have less durability than this value remaining to trigger the warning")
    private int threshold = 10;

    @ConfigEntry("message")
    @Comment("Allows changing the formatting of the warning message")
    private String message = StringUtils.miniMessageToLegacy("<gold>Your <dark_aqua>{0} <gold>only has <red>{1} <gold>durability remaining!");

    public LowDurabilityWarningTweak() {
        super("low-durability-warning", Category.ITEM);
        this.description = List.of(ChatColor.GRAY + "Warns players when an items durability is getting low.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (!isEnabled(player) || !(item.getItemMeta() instanceof Damageable damageable)) {
            return;
        }

        int remaining = item.getType().getMaxDurability() - damageable.getDamage() - event.getDamage() + 1;
        if (remaining > this.threshold || remaining <= 0) {
            return;
        }

        String name = damageable.hasDisplayName() ? damageable.getDisplayName() : StringUtils.capitalizeAll(item.getType().getKey().getKey().replace('_', ' '));

        player.sendMessage(MessageFormat.format(this.message, name, remaining));
    }
}
