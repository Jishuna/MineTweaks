package me.jishuna.minetweaks.tweak.farming;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.Utils;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class SmallFlowerBonemealTweak extends Tweak {

    @ConfigEntry("enable-for-players")
    @Comment("Lets players use bonemeal on small flowers")
    private boolean enablePlayer = true;

    @ConfigEntry("enable-for-dispensers")
    @Comment("Lets dispensers use bonemeal on small flowers")
    private boolean enableDispenser = true;

    @ConfigEntry("flowers")
    @Comment("List of blocks that can be duplicated with bonemeal")
    private Set<Material> flowers = Set
            .of(Material.POPPY, Material.DANDELION, Material.BLUE_ORCHID,
                    Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP,
                    Material.WHITE_TULIP, Material.ORANGE_TULIP, Material.PINK_TULIP,
                    Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY);

    public SmallFlowerBonemealTweak() {
        super("small-flower-bonemealing", Category.FARMING);
        this.description = List
                .of(ChatColor.GRAY + "Allows players and dispensers to quickly duplicate small flowers with bonemeal.", "",
                        ChatColor.GRAY + "Enabled For Players: %player%",
                        ChatColor.GRAY + "Enabled For Dispensers: %dispenser%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map
                .of("%player%", Utils.getDisplayString(this.enablePlayer),
                        "%dispenser%", Utils.getDisplayString(this.enableDispenser));
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!this.enablePlayer) {
            return;
        }

        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || item == null || item.getType() != Material.BONE_MEAL || !this.flowers.contains(block.getType())) {
            return;
        }

        block.getDrops().forEach(drop -> block.getWorld().dropItem(block.getLocation().add(0.5, 0, 0.5), drop));

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockDispense(BlockDispenseEvent event) {
        if (!this.enableDispenser || event.getBlock().getType() != Material.DISPENSER) {
            return;
        }

        ItemStack item = event.getItem();
        Directional directional = (Directional) event.getBlock().getBlockData();
        BlockFace face = directional.getFacing();
        Block block = event.getBlock().getRelative(face);

        if (item.getType() != Material.BONE_MEAL || !this.flowers.contains(block.getType())) {
            return;
        }

        block.getDrops().forEach(drop -> block.getWorld().dropItem(block.getLocation().add(0.5, 0, 0.5), drop));
        ((Container) event.getBlock().getState()).getInventory().removeItem(new ItemStack(Material.BONE_MEAL));
    }
}
