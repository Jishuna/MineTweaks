package me.jishuna.minetweaks.nms.latest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBlock;
import me.jishuna.minetweaks.nms.NMSAdapter;

public class NMSAdapterImpl implements NMSAdapter {

    @Override
    public void createMovingBlock(Location location, BlockFace face, boolean extending, boolean source) {
        Direction direction = CraftBlock.blockFaceToNotch(face);
        BlockPos blockPos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ServerLevel world = ((CraftWorld) location.getWorld()).getHandle();
        BlockState moving = world.getBlockState(blockPos);
        BlockPos target = blockPos.relative(direction);
        BlockState piston = Blocks.MOVING_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, direction);
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        world.setBlock(target, piston, 68);
        PistonMovingBlockEntity movingBlock = new CustomPistonMovingBlockEntity(target, new WrappedBlockState(piston), moving, direction, extending, source, blockEntity);
        world.setBlockEntity(movingBlock);

        if (blockEntity != null) {
            world.removeBlockEntity(blockPos);
        }
        world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 82);
    }

    @Override
    public void updatePiston(Location location, boolean extending) {
        BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ServerLevel world = ((CraftWorld) location.getWorld()).getHandle();

        BlockState state = world.getBlockState(pos).setValue(PistonBaseBlock.EXTENDED, extending);
        world.setBlock(pos, state, 67);
        world.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
        world.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
    }
}
