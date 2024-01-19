package me.jishuna.minetweaks.tweak.misc;

import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.inventory.PaintingSelectorGUI;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.ToggleableTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class PaintingSelectorTweak extends Tweak implements ToggleableTweak {

    @ConfigEntry("messages.gui-name")
    private String guiName = StringUtils.miniMessageToLegacy("<black>Painting Selector");

    @ConfigEntry("messages.painting-name")
    private String paintingName = StringUtils.miniMessageToLegacy("<white>{0}");

    @ConfigEntry("messages.painting-lore")
    private List<String> paintingLore = List
            .of("<white>Size: <gray>{0}x{1}", " ", "<gray>Click to select")
            .stream()
            .map(StringUtils::miniMessageToLegacy)
            .toList();

    @ConfigEntry("messages.no-space-name")
    private String noSpaceName = StringUtils.miniMessageToLegacy("<red>Not enough space for this painting.");

    @ConfigEntry("messages.no-space-lore")
    private List<String> noSpaceLore = Collections.emptyList();

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
            new PaintingSelectorGUI(this, painting).open(player);
        }
    }

    public String getGuiName() {
        return this.guiName;
    }

    public String getPaintingName() {
        return this.paintingName;
    }

    public List<String> getPaintingLore() {
        return this.paintingLore;
    }

    public String getNoSpaceName() {
        return this.noSpaceName;
    }

    public List<String> getNoSpaceLore() {
        return this.noSpaceLore;
    }
}
