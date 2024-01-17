package me.jishuna.minetweaks.tweak.mob;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class WitherMinionsTweak extends Tweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:minions");

    @ConfigEntry("amount")
    @Comment("The amount of wither skeletons that should be spawned")
    private int amount = 3;

    public WitherMinionsTweak() {
        super("wither-minions", Category.MOB);
        this.description = List.of(ChatColor.GRAY + "Withers spawn" + ChatColor.GREEN + " %amount% " + ChatColor.GRAY + "wither skeletons when reduced to half health.");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%amount%", ChatColor.GREEN.toString() + this.amount);
    }

    @EventHandler(ignoreCancelled = true)
    private void onDamage(EntityDamageEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Wither wither)) {
            return;
        }

        double health = wither.getHealth() - event.getFinalDamage();
        if (wither.getPersistentDataContainer().has(KEY, PDCTypes.BOOLEAN) || health <= 0
                || health > (wither.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2)) {
            return;
        }

        wither.getPersistentDataContainer().set(KEY, PDCTypes.BOOLEAN, true);

        for (int i = 0; i < this.amount; i++) {
            wither.getWorld().spawn(wither.getLocation(), WitherSkeleton.class);
        }
    }

}
