package me.jishuna.minetweaks.tweak.mob;

import java.util.Collections;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class EndermanGriefingTweak extends Tweak {

    public EndermanGriefingTweak() {
        this.name = "disable-endermen-griefing";
        this.category = Category.MOB;
        this.listenedEvents = Collections.singletonList(EntityChangeBlockEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        EntityChangeBlockEvent event = (EntityChangeBlockEvent) context.getEvent();
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
