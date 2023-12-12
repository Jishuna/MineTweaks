package me.jishuna.minetweaks.tweak.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.pdc.PDCUtils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class TorchArrowTweak extends Tweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:torch_arrow");

    @ConfigEntry("name")
    @Comment("The custom name of the torch arrow item")
    private final String itemName = ChatColor.YELLOW + "Torch Arrow";

    @ConfigEntry("recipe")
    @Comment("The recipe to craft a torch arrow")
    private final Recipe recipe = getDefaultRecipe();

    public TorchArrowTweak() {
        this.name = "torch-arrow";
        this.category = Category.ITEM;

        registerEventConsumer(EntityShootBowEvent.class, this::onBowShoot);
        registerEventConsumer(ProjectileHitEvent.class, this::onProjectileHit);
    }

    @Override
    public void load() {
        super.load();

        ItemStack item = ItemBuilder
                .modifyItem(this.recipe.getResult())
                .name(this.itemName)
                .persistentData(KEY, PDCTypes.BOOLEAN, true)
                .build();

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

    private void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player) || PDCUtils.get(KEY, PDCTypes.BOOLEAN, event.getConsumable()) == null) {
            return;
        }

        event.setConsumeItem(true);
        PDCUtils.set(KEY, PDCTypes.BOOLEAN, event.getProjectile(), true);
    }

    private void onProjectileHit(ProjectileHitEvent event) {
        if (event.getHitBlockFace() == null || PDCUtils.get(KEY, PDCTypes.BOOLEAN, event.getEntity()) == null) {
            return;
        }

        event.getEntity().remove();

        Block block = event.getHitBlock().getRelative(event.getHitBlockFace());
        if (!Tag.REPLACEABLE.isTagged(block.getType())) {
            return;
        }

        BlockData data = Material.TORCH.createBlockData();

        if (data.isSupported(block)) {
            block.setBlockData(data);
            return;
        }

        Directional directional = (Directional) Material.WALL_TORCH.createBlockData();
        for (BlockFace face : directional.getFaces()) {
            directional.setFacing(face);
            if (directional.isSupported(block)) {
                block.setBlockData(directional);
                return;
            }
        }
    }

    private static Recipe getDefaultRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(KEY, new ItemStack(Material.ARROW));
        recipe.addIngredient(Material.ARROW);
        recipe.addIngredient(Material.TORCH);

        return recipe;
    }
}
