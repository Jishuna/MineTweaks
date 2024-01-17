package me.jishuna.minetweaks.tweak.item;

import java.util.List;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import me.jishuna.jishlib.pdc.PDCTypes;
import me.jishuna.jishlib.pdc.PDCUtils;
import me.jishuna.minetweaks.NamespacedKeys;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;

@RegisterTweak
public class TorchArrowTweak extends CustomItemTweak {
    private static final NamespacedKey KEY = NamespacedKey.fromString("minetweaks:torch_arrow");

    public TorchArrowTweak() {
        super("torch-arrows", Category.ITEM, ChatColor.WHITE + "Torch Arrow", KEY);
        this.description = List
                .of(ChatColor.GRAY + "Allows the crafting of torch arrows with a torch and an arrow.",
                        ChatColor.GRAY + "Torch arrows will place torches wherever they land.", "",
                        ChatColor.GREEN + "Click to view recipe.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)
                || !Objects.equals(PDCUtils.get(NamespacedKeys.CUSTOM_ITEM, PDCTypes.NAMESPACE, event.getConsumable()), KEY)) {
            return;
        }

        event.setConsumeItem(true);
        PDCUtils.set(KEY, PDCTypes.BOOLEAN, event.getProjectile(), true);
    }

    @EventHandler(ignoreCancelled = true)
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

    @Override
    protected Recipe getDefaultRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(KEY, new ItemStack(Material.ARROW));
        recipe.addIngredient(Material.ARROW);
        recipe.addIngredient(Material.TORCH);

        return recipe;
    }
}
