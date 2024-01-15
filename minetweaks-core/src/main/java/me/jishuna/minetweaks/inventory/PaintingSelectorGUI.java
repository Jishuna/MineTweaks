package me.jishuna.minetweaks.inventory;

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
import me.jishuna.jishlib.item.provider.ItemProvider;
import me.jishuna.jishlib.item.provider.TranslatedItemProvider;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.jishlib.util.StringUtils;

public class PaintingSelectorGUI extends PagedCustomInventory<Art, Inventory> {
    private static final List<Art> ALL_ART = Arrays.asList(Art.values());
    private static final ItemProvider NO_SPACE = TranslatedItemProvider.create(Material.BARRIER, "gui.painting.no-space.name", "gui.painting.no-space.lore");

    private final Painting painting;

    public PaintingSelectorGUI(Painting painting) {
        super(Bukkit.createInventory(null, 54, MessageAPI.get("gui.painting.name")), ALL_ART, 45);
        this.painting = painting;

        cancelAllClicks();

        populate();
        refreshOptions();
    }

    private void populate() {
        setItem(InventoryConstants.FILLER, 45, 46, 47, 48, 49, 50, 51, 52, 53);
    }

    @Override
    protected ItemStack asItemStack(Art art) {
        String name = StringUtils.capitalizeAll(art.getKey().getKey().replace('_', ' '));
        int width = art.getBlockWidth();
        int height = art.getBlockHeight();

        ItemBuilder builder = ItemBuilder
                .create(Material.PAINTING)
                .name(MessageAPI.get("gui.painting.painting-name", name))
                .lore(MessageAPI.getList("gui.painting.painting-lore", width, height))
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
            replaceItem(event.getSlot(), NO_SPACE, 60);
        }
    }

}
