package me.jishuna.minetweaks.tweak.block.piston;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;
import me.jishuna.minetweaks.nms.NMS;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;

@RegisterTweak
public class BetterPistonsTweak extends Tweak {

    public BetterPistonsTweak() {
        super("better-pistons", Category.BLOCK);
        this.description = List
                .of(ChatColor.GRAY + "Makes both sides of a double door open and close together.", "",
                        ChatColor.GRAY + "Enabled For Players: %player%",
                        ChatColor.GRAY + "Enabled For Redstone: %redstone%");
    }

    @EventHandler(ignoreCancelled = true)
    private void onPistonExtend(BlockPhysicsEvent event) {
        if (event.getChangedType() != Material.STICKY_PISTON && event.getChangedType() != Material.PISTON) {
            return;
        }

        Block block = event.getBlock();
        NMS.getAdapter().activatePiston(block.getLocation(), block.getBlockData());
        event.setCancelled(true);
    }
}
