package me.jishuna.minetweaks.tweak.block;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import me.jishuna.jishlib.BlockVector;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class FastLeafDecayTweak extends Tweak {

    @ConfigEntry("search-radius")
    @Comment("The radius to search for leaves that should decay when a log is broken")
    private int searchRadius = 7;

    public FastLeafDecayTweak() {
        super("fast-leaf-decay", Category.BLOCK);
        this.description = List.of(ChatColor.GRAY + "Makes leaves decay much faster when the logs supporting them are broken.");
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!Tag.LOGS_THAT_BURN.isTagged(block.getType())) {
            return;
        }

        JishLib.runLaterAsync(() -> {
            List<BlockVector> blocks = getDecayableLeaves(block);
            if (!blocks.isEmpty()) {
                new BlockDecayRunnable(blocks, block.getWorld()).runTaskTimer(JishLib.getPlugin(), 1, 1);
            }
        }, 20);
    }

    private List<BlockVector> getDecayableLeaves(Block source) {
        List<BlockVector> blocks = new ArrayList<>();

        for (int x = -this.searchRadius; x <= this.searchRadius; x++) {
            for (int y = -this.searchRadius; y <= this.searchRadius; y++) {
                for (int z = -this.searchRadius; z <= this.searchRadius; z++) {
                    Block block = source.getRelative(x, y, z);
                    if (!(block.getBlockData() instanceof Leaves leaves)) {
                        continue;
                    }

                    if (!leaves.isPersistent() && leaves.getDistance() >= 7) {
                        blocks.add(BlockVector.fromLocation(block.getLocation()));
                    }
                }
            }
        }

        return blocks;
    }
}
