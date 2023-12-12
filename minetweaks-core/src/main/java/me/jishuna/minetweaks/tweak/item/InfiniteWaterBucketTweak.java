package me.jishuna.minetweaks.tweak.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class InfiniteWaterBucketTweak extends Tweak {

    @ConfigEntry("level-cost")
    @Comment("The number of levels required to apply infinity to a water bucket")
    private final int cost = 20;

    public InfiniteWaterBucketTweak() {
        this.name = "infinite-water-bucket";
        this.category = Category.ITEM;

        registerEventConsumer(PrepareAnvilEvent.class, this::onPrepareAnvil);
        registerEventConsumer(PlayerBucketEmptyEvent.class, this::onBucketEmpty);
    }

    private void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack first = inventory.getItem(0);
        ItemStack second = inventory.getItem(1);

        if (first == null || first.getType() != Material.WATER_BUCKET || first.containsEnchantment(Enchantment.ARROW_INFINITE) ||
                second == null || second.getType() != Material.ENCHANTED_BOOK) {
            return;
        }

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) second.getItemMeta();
        if (meta.hasStoredEnchant(Enchantment.ARROW_INFINITE)) {
            event.setResult(ItemBuilder.modifyClone(first).name(inventory.getRenameText()).enchantment(Enchantment.ARROW_INFINITE, 1).build());
            inventory.setRepairCost(this.cost);
        }
    }

    private void onBucketEmpty(PlayerBucketEmptyEvent event) {
        EntityEquipment equipment = event.getPlayer().getEquipment();
        ItemStack item = equipment.getItem(event.getHand());
        if (item.getType() != Material.WATER_BUCKET) {
            return;
        }

        if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
            JishLib.run(() -> equipment.setItem(event.getHand(), item));
        }
    }
}
