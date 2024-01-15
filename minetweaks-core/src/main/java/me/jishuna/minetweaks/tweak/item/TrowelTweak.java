package me.jishuna.minetweaks.tweak.item;

import java.util.ArrayList;
import java.util.List;
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
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.BoundingBox;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.pdc.PDCUtils;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.Utils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class TrowelTweak extends Tweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:trowel");

    @ConfigEntry("name")
    @Comment("The custom name of the trowel item")
    private String itemName = ChatColor.WHITE + "Trowel";

    @ConfigEntry("custom-model-data")
    @Comment("The custom model data for this item, used with resource packs.")
    private int modelData = 0;

    @ConfigEntry("recipe")
    @Comment("The recipe to craft a trowel")
    private Recipe recipe = getDefaultRecipe();

    public TrowelTweak() {
        super("trowel", Category.ITEM);
        this.description = List
                .of(ChatColor.GRAY + "Allows the crafting of a trowel with a stick and an iron ingot.",
                        ChatColor.GRAY + "Can be used as a shovel or to place a random block from your hotbar when right clicking.");
    }

    @Override
    public void reload() {
        super.reload();

        Bukkit.removeRecipe(KEY);
        if (this.enabled) {
            setup();
        }
    }

    private void setup() {
        ItemStack item = ItemBuilder
                .modifyItem(this.recipe.getResult())
                .name(this.itemName)
                .modelData(this.modelData)
                .persistentData(KEY, PDCTypes.BOOLEAN, true)
                .build();

        Registries.ITEM.register(KEY, item, true);

        if (this.recipe instanceof ShapedRecipe shaped) {
            ShapedRecipe finalRecipe = new ShapedRecipe(KEY, item);
            finalRecipe.shape(shaped.getShape());
            shaped.getChoiceMap().forEach(finalRecipe::setIngredient);

            Bukkit.addRecipe(finalRecipe);
        } else if (this.recipe instanceof ShapelessRecipe shapeless) {
            ShapelessRecipe finalRecipe = new ShapelessRecipe(KEY, item);
            shapeless.getChoiceList().forEach(finalRecipe::addIngredient);

            Bukkit.addRecipe(finalRecipe);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || PDCUtils.get(KEY, PDCTypes.BOOLEAN, event.getItem()) == null) {
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
            Utils.reduceDurability(event.getPlayer(), event.getItem(), event.getHand());
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

    private static Recipe getDefaultRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(KEY, new ItemStack(Material.IRON_SHOVEL));
        recipe.shape("020", "010");
        recipe.setIngredient('1', Material.STICK);
        recipe.setIngredient('2', Material.IRON_INGOT);

        return recipe;
    }
}
