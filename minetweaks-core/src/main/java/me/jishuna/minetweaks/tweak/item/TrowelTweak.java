package me.jishuna.minetweaks.tweak.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundGroup;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.BoundingBox;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.pdc.PDCUtils;
import me.jishuna.jishlib.util.ItemUtils;
import me.jishuna.minetweaks.NamespacedKeys;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;

@RegisterTweak
public class TrowelTweak extends CustomItemTweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:trowel");

    public TrowelTweak() {
        super("trowel", Category.ITEM, ChatColor.WHITE + "Trowel", KEY);
        this.description = List
                .of(ChatColor.GRAY + "Allows the crafting of a trowel with a stick and an iron ingot.",
                        ChatColor.GRAY + "Can be used as a shovel or to place a random block from your hotbar when right clicking.", "",
                        ChatColor.GREEN + "Click to view recipe.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || !Objects.equals(PDCUtils.get(NamespacedKeys.CUSTOM_ITEM, PDCTypes.NAMESPACE, event.getItem()), KEY)) {
            return;
        }

        event.setUseInteractedBlock(Result.DENY);

        Block clicked = event.getClickedBlock();
        Block target = clicked.getRelative(event.getBlockFace());
        Player player = event.getPlayer();
        if (!Tag.REPLACEABLE.isTagged(target.getType()) || BoundingBox.of(target).overlaps(player.getBoundingBox())) {
            return;
        }

        ItemStack toPlace = getItemToPlace(player.getInventory());
        if (toPlace == null) {
            return;
        }

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(target, target.getState(), clicked, toPlace, player, true, event.getHand());
        Bukkit.getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled()) {
            return;
        }

        BlockData data = toPlace.getType().createBlockData();
        target.setBlockData(data);

        SoundGroup group = data.getSoundGroup();
        player.playSound(target.getLocation(), group.getPlaceSound(), group.getVolume(), group.getPitch());

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            toPlace.setAmount(toPlace.getAmount() - 1);
            ItemUtils.reduceDurability(event.getPlayer(), event.getItem(), event.getHand());
        }
    }

    private ItemStack getItemToPlace(PlayerInventory inventory) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType().isBlock()) {
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            return null;
        }

        return items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }

    @Override
    protected Recipe getDefaultRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(KEY, new ItemStack(Material.IRON_SHOVEL));
        recipe.shape("2", "1");
        recipe.setIngredient('1', Material.STICK);
        recipe.setIngredient('2', Material.IRON_INGOT);

        return recipe;
    }
}
