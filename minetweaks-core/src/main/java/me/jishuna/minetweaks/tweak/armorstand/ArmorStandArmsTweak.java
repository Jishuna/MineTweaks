package me.jishuna.minetweaks.tweak.armorstand;

import java.util.Collections;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityPlaceEvent;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class ArmorStandArmsTweak extends Tweak {

    public ArmorStandArmsTweak() {
        this.name = "armor-stand-arms";
        this.category = Category.ARMOR_STAND;
        this.listenedEvents = Collections.singletonList(EntityPlaceEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        if (context.getEntity() instanceof ArmorStand stand) {
            stand.setArms(true);
        }
    }

}
