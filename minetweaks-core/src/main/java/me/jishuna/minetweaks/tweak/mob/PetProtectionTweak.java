package me.jishuna.minetweaks.tweak.mob;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.jishuna.jishlib.util.EventUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class PetProtectionTweak extends Tweak implements ToggleableTweak {

    public PetProtectionTweak() {
        super("pet-protection", Category.MOB);
        this.description = List.of(ChatColor.GRAY + "Prevents players from accidently hurting their pets with melee attacks or projectiles.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Tameable tamable) || tamable.getOwner() == null) {
            return;
        }

        Entity damager = EventUtils.getAttackingEntity(event);
        if (damager == null || !(damager instanceof Player player) || !isEnabled(player)) {
            return;
        }

        if (tamable.getOwner().equals(player)) {
            event.setCancelled(true);
        }
    }
}
