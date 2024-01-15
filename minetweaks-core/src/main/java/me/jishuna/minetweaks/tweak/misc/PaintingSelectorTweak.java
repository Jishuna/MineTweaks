package me.jishuna.minetweaks.tweak.misc;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.jishuna.minetweaks.inventory.PaintingSelectorGUI;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class PaintingSelectorTweak extends Tweak implements ToggleableTweak {

    public PaintingSelectorTweak() {
        super("painting-selector", Category.MISC);
        this.description = List.of(ChatColor.GRAY + "Allows players to change the design of paintings by right clicking them while sneaking.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!isEnabled(player)) {
            return;
        }

        if (event.getHand() == EquipmentSlot.HAND && player.isSneaking() && event.getRightClicked() instanceof Painting painting) {
            new PaintingSelectorGUI(painting).open(player);
        }
    }
}
