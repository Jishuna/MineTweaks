package me.jishuna.minetweaks.tweak.misc;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class BloodEffectTweak extends Tweak implements ToggleableTweak {

    @ConfigEntry("particle-count")
    @Comment("The number of particles to spawn.")
    private int count = 10;

    @ConfigEntry("particle-type")
    @Comment("The block type to use for the blood effect.")
    private Material type = Material.RED_CONCRETE;

    public BloodEffectTweak() {
        super("blood-effect", Category.MISC);
        this.description = List.of(ChatColor.GRAY + "Allows players to see a blood effect when attacking enemies.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player) || !isEnabled(player) ||
                !(event.getEntity() instanceof LivingEntity living) || event.getEntityType() == EntityType.ARMOR_STAND) {
            return;
        }

        double halfHeight = living.getHeight() / 2;
        double width = living.getWidth() / 4;

        player.spawnParticle(Particle.BLOCK_DUST, living.getLocation().add(0, halfHeight, 0), this.count, width, halfHeight / 2, width, 0, this.type.createBlockData());
    }
}
