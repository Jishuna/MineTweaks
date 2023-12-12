package me.jishuna.minetweaks.tweak.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityPlaceEvent;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class ArmorStandArmsTweak extends Tweak {

    public ArmorStandArmsTweak() {
        this.name = "armor-stand-arms";
        this.category = Category.ARMOR_STAND;

        registerEventConsumer(EntityPlaceEvent.class, this::onEntityPlace);
    }

    private void onEntityPlace(EntityPlaceEvent event) {
        if (event.getEntity() instanceof ArmorStand stand) {
            stand.setArms(true);
        }
    }

}
