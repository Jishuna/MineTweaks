package me.jishuna.minetweaks.tweak.block;

import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.jishuna.minetweaks.gui.PaintingSelectorGUI;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class PaintingSelectorTweak extends Tweak {

    public PaintingSelectorTweak() {
        this.name = "painting-selector";
        this.category = Category.BLOCK;

        registerEventConsumer(PlayerInteractEntityEvent.class, this::onInteractEntity);
    }

    private void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getHand() == EquipmentSlot.HAND && player.isSneaking() && event.getRightClicked() instanceof Painting painting) {
            new PaintingSelectorGUI(painting).open(player);
        }
    }
}
