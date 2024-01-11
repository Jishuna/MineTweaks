package me.jishuna.minetweaks.tweak.block;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import me.jishuna.jishlib.BlockVector;
import me.jishuna.minetweaks.tweak.RegisterTweak;

@RegisterTweak
public class BlockDecayRunnable extends BukkitRunnable {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final List<BlockVector> blocks;
    private final World world;
    private final int blocksPerTick;

    public BlockDecayRunnable(List<BlockVector> blocks, World world) {
        this.blocks = blocks;
        this.world = world;
        this.blocksPerTick = (int) Math.ceil(blocks.size() / 100d);
    }

    @Override
    public void run() {
        for (int i = 0; i < this.blocksPerTick; i++) {
            BlockVector vector = this.blocks.remove(this.random.nextInt(this.blocks.size()));

            Block block = vector.toLocation(this.world).getBlock();
            if (Tag.LEAVES.isTagged(block.getType())) {
                block.breakNaturally();
            }

            if (this.blocks.isEmpty()) {
                cancel();
                return;
            }
        }
    }
}
