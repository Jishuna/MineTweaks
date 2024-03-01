package me.jishuna.minetweaks.inventory;

import java.util.Collection;
import java.util.Comparator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.inventory.PagedCustomInventory;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.message.MessageAPI;
import me.jishuna.jishlib.util.Utils;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.tweak.Tweak;

public class TweakListInventory extends PagedCustomInventory<Tweak, Inventory> {
    private static final PlayerProfile ENABLED = Utils.createProfile("ac01f6796eb63d0e8a759281d037f7b3843090f9a456a74f786d049065c914c7");
    private static final PlayerProfile DISABLED = Utils.createProfile("548d7d1e03e1af145b0125ab841285672b421265da2ab915015f9058438ba2d8");

    public TweakListInventory() {
        super(Bukkit.createInventory(null, 54, MessageAPI.getLegacy("gui.tweak.name", Registries.TWEAK.size())), getSortedTweaks(), 45);

        cancelAllClicks();

        populate();
        refreshOptions();
    }

    private void populate() {
        setButton(45, InventoryConstants.PREVIOUS_PAGE, (event, session) -> changePage(-1));
        setButton(53, InventoryConstants.NEXT_PAGE, (event, session) -> changePage(1));
        setButton(49, InventoryConstants.CLOSE_INVENTORY);

        setItem(InventoryConstants.FILLER, 46, 47, 48, 50, 51, 52);
    }

    @Override
    protected ItemStack asItemStack(Tweak tweak) {
        String status = me.jishuna.minetweaks.util.Utils.getDisplayString(tweak.isEnabled());

        return ItemBuilder
                .create(Material.PLAYER_HEAD)
                .name(MessageAPI.getLegacy("tweak.name", tweak.getDisplayName(), status))
                .lore(tweak.getDescription())
                .skullProfile(tweak.isEnabled() ? ENABLED : DISABLED)
                .build();
    }

    @Override
    protected void onItemClicked(InventoryClickEvent event, InventorySession session, Tweak tweak) {
        tweak.onMenuClick(event, session);
    }

    private static Collection<Tweak> getSortedTweaks() {
        return Registries.TWEAK.getValues().stream().sorted(Comparator.comparing(Tweak::getDisplayName)).toList();
    }
}
