package me.jishuna.minetweaks.inventory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Painting;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.minetweaks.tweak.misc.PaintingSelectorTweak;

public class PaintingSelectorGUI extends PagedCustomInventory<Art, Inventory> {
    private static final List<Art> ALL_ART = Arrays.asList(Art.values());

    private final PaintingSelectorTweak tweak;
    private final Painting painting;

    public PaintingSelectorGUI(PaintingSelectorTweak tweak, Painting painting) {
        super(Bukkit.createInventory(null, 54, tweak.getGuiName()), ALL_ART, 45);
        this.tweak = tweak;
        this.painting = painting;

        cancelAllClicks();

        populate();
        refreshOptions();
    }

    private void populate() {
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.FILLER, 45, 46, 47, 48, 50, 51, 52, 53);
    }

    @Override
    protected ItemStack asItemStack(Art art) {
        String name = StringUtils.capitalizeAll(art.getKey().getKey().replace('_', ' '));
        int width = art.getBlockWidth();
        int height = art.getBlockHeight();

        ItemBuilder builder = ItemBuilder
                .create(Material.PAINTING)
                .name(MessageFormat.format(this.tweak.getPaintingName(), name))
                .lore(this.tweak.getPaintingLore().stream().map(string -> MessageFormat.format(string, width, height)).toList())
                .hideAll();

        if (art == this.painting.getArt()) {
            builder.enchantment(Enchantment.DURABILITY, 1);
        }

        return builder.build();
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, Art art) {
        if (this.painting.getArt() == art) {
            return;
        }

        if (this.painting.setArt(art)) {
            refreshOptions();
        } else {
            replaceItem(event.getSlot(), ItemBuilder
                    .create(Material.BARRIER)
                    .name(this.tweak.getNoSpaceName())
                    .lore(this.tweak.getNoSpaceLore())
                    .build(), 60);
        }
    }

}
