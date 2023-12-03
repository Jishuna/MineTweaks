package me.jishuna.minetweaks.tweak.block;

import java.util.Collections;
import org.bukkit.entity.Painting;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.gui.PaintingSelectorGUI;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class PaintingSelectorTweak extends Tweak {

    public PaintingSelectorTweak() {
        this.name = "painting-selector";
        this.category = Category.BLOCK;
        this.listenedEvents = Collections.singletonList(PlayerInteractEntityEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        PlayerInteractEntityEvent event = (PlayerInteractEntityEvent) context.getEvent();

        if (event.getHand() == EquipmentSlot.HAND && context.getPlayer().isSneaking() && context.getEntity() instanceof Painting painting) {
            new PaintingSelectorGUI(painting).open(context.getPlayer());
        }
    }
}
