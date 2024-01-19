package me.jishuna.minetweaks.tweak.mob;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityInteractEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class NoMobTrampling extends Tweak {
    public NoMobTrampling() {
        super("no-mob-trampling", Category.MOB);
        this.description = List
                .of(ChatColor.GRAY + "Prevents mobs from trampling farmland.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityTrample(EntityInteractEvent event) {
        if (event.getEntityType() != EntityType.PLAYER && event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
