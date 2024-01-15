package me.jishuna.minetweaks.tweak.item;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class WearableBanners extends Tweak {

    public WearableBanners() {
        super("wearable-banners", Category.ITEM);
        this.description = List.of(ChatColor.GRAY + "Allows players to wear any type of banner on their head.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() != 5 || !(event.getClickedInventory() instanceof PlayerInventory inventory)) {
            return;
        }

        ItemStack banner = event.getCursor();
        if (!Tag.BANNERS.isTagged(banner.getType())) {
            return;
        }

        event.getView().setCursor(event.getCurrentItem());
        inventory.setHelmet(banner);
        event.setCancelled(true);
    }
}
