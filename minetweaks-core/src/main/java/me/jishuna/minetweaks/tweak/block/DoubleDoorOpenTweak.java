package me.jishuna.minetweaks.tweak.block;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joml.Math;
import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.minetweaks.tweak.Category;
import me.jishuna.minetweaks.tweak.RegisterTweak;
import me.jishuna.minetweaks.tweak.Tweak;
import me.jishuna.minetweaks.util.Utils;

@RegisterTweak
public class DoubleDoorOpenTweak extends Tweak {

    @ConfigEntry("enable-for-players")
    @Comment("Makes double doors open together when opened by players")
    private boolean enablePlayer = true;

    @ConfigEntry("enable-for-redstone")
    @Comment("Makes double doors open together when opened by redstone")
    private boolean enableRedstone = true;

    public DoubleDoorOpenTweak() {
        super("double-door-opening", Category.BLOCK);
        this.description = List
                .of(ChatColor.GRAY + "Makes both sides of a double door open and close together.", "",
                        ChatColor.GRAY + "Enabled For Players: %player%",
                        ChatColor.GRAY + "Enabled For Redstone: %redstone%");
    }

    @Override
    public Map<String, Object> getPlaceholders() {
        return Map
                .of("%player%", Utils.getDisplayString(this.enablePlayer),
                        "%redstone%", Utils.getDisplayString(this.enableRedstone));
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (!this.enablePlayer || event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK
                || !Tag.WOODEN_DOORS.isTagged(block.getType())) {
            return;
        }

        Door door = (Door) block.getBlockData();
        handleConnectedDoor(block, door, !door.isOpen(), false);
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockRedstone(BlockRedstoneEvent event) {
        Block block = event.getBlock();
        if (!this.enableRedstone || !Tag.DOORS.isTagged(block.getType())) {
            return;
        }

        int current = event.getNewCurrent();
        Door door = (Door) block.getBlockData();
        boolean matching = handleConnectedDoor(block, door, current > 0, true) == door.isOpen();

        if (matching) {
            if (door.isOpen()) {
                event.setNewCurrent(Math.max(1, current));
            } else {
                event.setNewCurrent(0);
            }
        }
    }

    private boolean handleConnectedDoor(Block block, Door source, boolean open, boolean redstone) {
        Block otherDoor = getConnectedDoor(block, source);
        if (otherDoor == null || (otherDoor.getType() == Material.IRON_DOOR && !redstone)) {
            return open;
        }

        if (redstone && (block.getBlockPower() > 0 || otherDoor.getBlockPower() > 0)) {
            open = true;
        }

        Door other = (Door) otherDoor.getBlockData();
        if (other.isOpen() != open) {
            other.setOpen(open);
            otherDoor.setBlockData(other);
        }

        return open;
    }

    private Block getConnectedDoor(Block block, Door door) {
        BlockFace direction = door.getHinge() == Hinge.LEFT ? BlockFace.EAST : BlockFace.WEST;
        Block adjacent = block.getRelative(direction);
        if (!(adjacent.getBlockData() instanceof Door other) || door.getHalf() != other.getHalf() ||
                door.getFacing() != other.getFacing() || door.getHinge() == other.getHinge()) {
            return null;
        }

        return adjacent;
    }
}
