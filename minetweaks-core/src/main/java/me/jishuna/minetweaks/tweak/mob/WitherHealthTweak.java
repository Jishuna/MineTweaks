package me.jishuna.minetweaks.tweak.mob;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class WitherHealthTweak extends Tweak {

    @ConfigEntry("health")
    @Comment("The amount of health withers should have")
    private int health = 600;

    public WitherHealthTweak() {
        super("increased-wither-health", Category.MOB);
        this.description = List.of(ChatColor.GRAY + "Increases the health of the wither to %health%.");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%health%", ChatColor.GREEN.toString() + this.health);
    }

    @EventHandler(ignoreCancelled = true)
    private void onSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() || (event.getEntityType() != EntityType.WITHER)) {
            return;
        }

        LivingEntity entity = event.getEntity();
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        instance.setBaseValue(this.health);
        entity.setHealth(this.health);
    }
}
