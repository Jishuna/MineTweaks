package me.jishuna.minetweaks.tweak.misc;

import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class SwingThroughGrassTweak extends Tweak {

    @ConfigEntry("transparent-materials")
    @Comment("A list of materials that should not block attacks")
    private Set<Material> transparentMaterials = Set.of(Material.SHORT_GRASS, Material.TALL_GRASS, Material.FERN, Material.LARGE_FERN, Material.DEAD_BUSH);

    public SwingThroughGrassTweak() {
        super("swing-through-grass", Category.MISC);
        this.description = List.of(ChatColor.GRAY + "Allows attacking entities through tall grass blocks.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (event.useItemInHand() == Result.DENY || event.getHand() != EquipmentSlot.HAND
                || event.getAction() != Action.LEFT_CLICK_BLOCK || !this.transparentMaterials.contains(block.getType())) {
            return;
        }

        Player player = event.getPlayer();
        RayTraceResult result = player
                .getWorld()
                .rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 3.5, FluidCollisionMode.NEVER, true, 0, entity -> entity != player);

        if (result != null && result.getHitEntity() != null && result.getHitEntity() instanceof LivingEntity entity) {
            player.attack(entity);
        }
    }
}
