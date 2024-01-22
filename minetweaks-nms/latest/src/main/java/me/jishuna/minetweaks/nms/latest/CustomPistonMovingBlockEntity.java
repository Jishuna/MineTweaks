package me.jishuna.minetweaks.nms.latest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomPistonMovingBlockEntity extends PistonMovingBlockEntity {
    private final CompoundTag data;
    private boolean started = false;

    public CustomPistonMovingBlockEntity(BlockPos pos, BlockState state, BlockState pushedBlock, Direction facing, boolean extending, boolean source, BlockEntity blockEntity) {
        super(pos, state, pushedBlock, facing, extending, source);

        if (blockEntity != null) {
            this.data = blockEntity.saveWithoutMetadata();
        } else {
            this.data = null;
        }
    }

    public CustomPistonMovingBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.data = null;
    }

    @Override
    public void finalTick() {
        // TODO what does this do
    }

    public static void tick(Level world, BlockPos pos, BlockState state, PistonMovingBlockEntity blockEntity) {
        PistonMovingBlockEntity.tick(world, pos, state, blockEntity);

        if (!(blockEntity instanceof CustomPistonMovingBlockEntity custom)) {
            return;
        }

        BlockState block = world.getBlockState(pos);
        if (block.is(Blocks.MOVING_PISTON)) {
            custom.started = true;
        }

        if (custom.started && custom.data != null && !block.is(Blocks.MOVING_PISTON)) {
            BlockEntity target = world.getBlockEntity(pos);
            if (target == null) {
                return;
            }

            target.load(custom.data);
        }
    }
}
