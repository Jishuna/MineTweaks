package me.jishuna.minetweaks.inventory;

import java.util.Collection;
import java.util.Comparator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.Tweak;

public class TweakListInventory extends PagedCustomInventory<Tweak, Inventory> {

    public TweakListInventory() {
        super(Bukkit.createInventory(null, 54, "Tweaks"), getSortedTweaks(), 45);

        cancelAllClicks();
        refreshOptions();
    }

    @Override
    protected ItemStack asItemStack(Tweak tweak) {
        String status = tweak.isEnabled() ? MessageAPI.get("tweak.enabled") : MessageAPI.get("tweak.disabled");

        return ItemBuilder
                .create(Material.PAPER)
                .name(MessageAPI.get("tweak.name", tweak.getDisplayName(), status))
                .lore(tweak.getDescription())
                .build();
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, Tweak tweak) {
        // Do nothing
    }

    private static Collection<Tweak> getSortedTweaks() {
        return Registries.TWEAK.getValues().stream().sorted(Comparator.comparing(Tweak::getDisplayName)).toList();
    }
}
