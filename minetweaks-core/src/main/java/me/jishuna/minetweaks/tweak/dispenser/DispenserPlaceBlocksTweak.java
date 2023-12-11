package me.jishuna.minetweaks.tweak.dispenser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.EventContext;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.Tweak;

public class DispenserPlaceBlocksTweak extends Tweak {
    private static final Map<Material, Material> placementItems = new HashMap<>();

    static {
        for (Material material : Material.values()) {
            if (!material.isBlock()) {
                continue;
            }

            Material placementMaterial = material.createBlockData().getPlacementMaterial();
            if (placementMaterial != material) {
                placementItems.put(placementMaterial, material);
            }
        }
    }

    @ConfigEntry("blacklist")
    @Comment("Items in this list will not be placeable by dispensers")
    private final Set<Material> blacklist = Set.of(Material.TNT, Material.OBSIDIAN, Material.CRYING_OBSIDIAN);

    public DispenserPlaceBlocksTweak() {
        this.name = "dispenser-place-blocks";
        this.category = Category.DISPENSER;
        this.listenedEvents = Collections.singletonList(BlockDispenseEvent.class);
    }

    @Override
    public void handleEvent(EventContext<?> context) {
        BlockDispenseEvent event = (BlockDispenseEvent) context.getEvent();
        if (event.getBlock().getType() != Material.DISPENSER) {
            return;
        }

        Directional directional = (Directional) event.getBlock().getBlockData();
        BlockFace face = directional.getFacing();
        Block block = event.getBlock().getRelative(face);

        Material original = event.getItem().getType();
        Material type = placementItems.getOrDefault(original, original);

        if (this.blacklist.contains(type) || !type.isBlock()) {
            return;
        }

        event.setItem(new ItemStack(Material.AIR));

        if (block.getType().isAir() && type.createBlockData().isSupported(block)) {
            block.setType(type);
            ((Container) event.getBlock().getState()).getInventory().removeItem(new ItemStack(original));

            if (block.getBlockData() instanceof Directional dir && dir.getFaces().contains(directional.getFacing())) {
                dir.setFacing(directional.getFacing());
                block.setBlockData(dir);
            }
        }
    }
}
