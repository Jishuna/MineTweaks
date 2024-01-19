package me.jishuna.minetweaks.tweak.farming;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class FeatherFallingTramplingTweak extends Tweak {

    @ConfigEntry("min-level")
    @Comment("The minimum feather falling level needed to prevent trampling.")
    @Comment("Setting this to 0 will stop players from trampling farmland under any circumstance.")
    private int minLevel = 1;

    public FeatherFallingTramplingTweak() {
        super("feather-falling-prevents-trampling", Category.FARMING);
        this.description = List
                .of(ChatColor.GRAY + "Prevents players from trampling farmland when wearing feather falling boots.", "",
                        ChatColor.GRAY + "Minimum Enchantment Level: %min%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map.of("%min%", ChatColor.GREEN.toString() + this.minLevel);
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityTrample(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL || event.getClickedBlock().getType() != Material.FARMLAND) {
            return;
        }

        if (this.minLevel < 1) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getPlayer().getEquipment().getBoots();
        if (item == null || item.getType().isAir()) {
            return;
        }

        int level = item.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
        if (level >= this.minLevel) {
            event.setCancelled(true);
        }
    }
}
