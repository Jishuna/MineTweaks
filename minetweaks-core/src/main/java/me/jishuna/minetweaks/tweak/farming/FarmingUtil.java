package me.jishuna.minetweaks.tweak.farming;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import me.jishuna.jishlib.datastructure.WeightedRandom;
import me.jishuna.minetweaks.util.BlockColumnProcessor;

public class FarmingUtil {
    public static boolean tryGrowTallPlant(Block block, int maxHeight) {
        BlockColumnProcessor processor = new BlockColumnProcessor(block);
        int height = processor.getHeight();

        Block top = processor.getTopBlock();
        Ageable blockData = (Ageable) top.getBlockData();
        int age = blockData.getAge();

        if (height >= maxHeight) {
            return false;
        }

        if (age >= blockData.getMaximumAge()) {
            if (top.getY() >= top.getWorld().getMaxHeight() - 1) {
                return false;
            }

            top.getRelative(BlockFace.UP).setType(top.getType());
        } else {
            blockData.setAge(Math.min(age + ThreadLocalRandom.current().nextInt(2, 6), blockData.getMaximumAge()));
            top.setBlockData(blockData);
        }

        block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 15, 0.3d, 0.3d, 0.3d);
        return true;
    }

    public static void growPlants(Block base, WeightedRandom<Material> growthChances) {
        Random random = ThreadLocalRandom.current();
        int grown = 0;
        int counter = 0;

        while (grown < 10 && counter < 30) {
            counter++;

            Block target = base.getLocation().add(random.nextGaussian() * 2.5, 3, random.nextGaussian() * 2.5).getBlock();
            for (int i = 0; i < 6; i++) {
                if (target.getType().isAir() && target.getRelative(BlockFace.DOWN).getType() == base.getType()) {
                    target.setType(growthChances.poll());
                    grown++;
                    break;
                }

                target = target.getRelative(BlockFace.DOWN);
            }
        }

        if (grown > 0) {
            base.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, base.getLocation().add(0.5, 1.5, 0.5), 25, 2d, 0.5d, 2d);
        }
    }
}
