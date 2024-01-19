package me.jishuna.minetweaks.tweak.item;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.inventory.InventorySession;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.util.RecipeUtils;
import me.jishuna.minetweaks.NamespacedKeys;
import me.jishuna.minetweaks.Registries;
import me.jishuna.minetweaks.inventory.recipe.RecipeInventory;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public abstract class CustomItemTweak extends Tweak {
    private final NamespacedKey key;

    @ConfigEntry("name")
    @Comment("The custom name of this item")
    private String itemName = "";

    @ConfigEntry("custom-model-data")
    @Comment("The custom model data for this item, used with resource packs.")
    private int modelData = 0;

    @ConfigEntry("recipe")
    @Comment("The recipe to craft this item")
    private Recipe recipe = getDefaultRecipe();

    private RecipeInventory recipeInventory;

    protected CustomItemTweak(String name, Category category, String itemName, NamespacedKey key) {
        super(name, category);
        this.key = key;
        this.itemName = itemName;
    }

    @Override
    public void reload() {
        super.reload();

        Bukkit.removeRecipe(this.key);
        Registries.ITEM.remove(this.key);
        if (this.enabled) {
            setup();
        }
    }

    @Override
    public void onMenuClick(InventoryClickEvent event, InventorySession session) {
        if (this.recipeInventory != null) {
            session.changeTo(this.recipeInventory, true);
        }
    }

    private void setup() {
        ItemStack item = ItemBuilder
                .modifyItem(this.recipe.getResult())
                .name(this.itemName)
                .modelData(this.modelData)
                .persistentData(NamespacedKeys.CUSTOM_ITEM, PDCTypes.NAMESPACE, this.key)
                .hideAll()
                .build();

        Registries.ITEM.register(this.key, item, true);

        Recipe finalRecipe = RecipeUtils.copyRecipe(this.recipe, this.key, item);
        Bukkit.addRecipe(finalRecipe);

        this.recipeInventory = RecipeInventory.create(finalRecipe);
    }

    protected abstract Recipe getDefaultRecipe();
}
