package me.jishuna.minetweaks.tweak.mob;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class EndermanGriefingTweak extends Tweak {

    public EndermanGriefingTweak() {
        this.name = "disable-endermen-griefing";
        this.category = Category.MOB;

        registerEventConsumer(EntityChangeBlockEvent.class, this::onBlockChange);
    }

    private void onBlockChange(EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
