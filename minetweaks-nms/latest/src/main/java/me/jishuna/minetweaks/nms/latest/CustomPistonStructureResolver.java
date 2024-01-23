package me.jishuna.minetweaks.nms.latest;

//TODO clean up entire class
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class CustomPistonStructureResolver {
    public static final int MAX_PUSH_DEPTH = 1000;
    private final Level level;
    private final BlockPos pistonPos;
    private final boolean extending;
    private final BlockPos startPos;
    private final Direction pushDirection;
    private final List<BlockPos> toPush = Lists.newArrayList();
    private final List<BlockPos> toDestroy = Lists.newArrayList();
    private final Direction pistonDirection;

    public CustomPistonStructureResolver(Level world, BlockPos pos, Direction dir, boolean retracted) {
        this.level = world;
        this.pistonPos = pos;
        this.pistonDirection = dir;
        this.extending = retracted;
        if (retracted) {
            this.pushDirection = dir;
            this.startPos = pos.relative(dir);
        } else {
            this.pushDirection = dir.getOpposite();
            this.startPos = pos.relative(dir, 2);
        }

    }

    public boolean resolve() {
        this.toPush.clear();
        this.toDestroy.clear();
        BlockState blockState = this.level.getBlockState(this.startPos);
        if (!canPush(blockState, this.level, this.startPos, this.pushDirection, false, this.pistonDirection)) {
            if (this.extending && blockState.getPistonPushReaction() == PushReaction.DESTROY) {
                this.toDestroy.add(this.startPos);
                return true;
            } else {
                return false;
            }
        } else if (!this.addBlockLine(this.startPos, this.pushDirection)) {
            return false;
        } else {
            for(int i = 0; i < this.toPush.size(); ++i) {
                BlockPos blockPos = this.toPush.get(i);
                if (isSticky(this.level.getBlockState(blockPos)) && !this.addBranchingBlocks(blockPos)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isSticky(BlockState state) {
        return state.is(Blocks.SLIME_BLOCK) || state.is(Blocks.HONEY_BLOCK) || state.is(Blocks.CHAIN);
    }

    private static boolean canStickToEachOther(Level level, BlockPos pos, BlockState state, BlockPos adjacentPos, BlockState adjacentState, Direction direction, Direction moveDirection) {
        if (state.is(Blocks.CHAIN)) {
            Axis axis = state.getValue(RotatedPillarBlock.AXIS);
            if (axis != direction.getAxis()) {
                return false;
            }
            
            if (adjacentState.is(Blocks.CHAIN)) {
                return axis == adjacentState.getValue(RotatedPillarBlock.AXIS);
            }
            
            return adjacentState.isFaceSturdy(level, adjacentPos, direction.getOpposite(), SupportType.CENTER);
        }
        
        if (state.is(Blocks.HONEY_BLOCK) && adjacentState.is(Blocks.SLIME_BLOCK)) {
            return false;
        } else if (state.is(Blocks.SLIME_BLOCK) && adjacentState.is(Blocks.HONEY_BLOCK)) {
            return false;
        } else {
            return isSticky(state) || isSticky(adjacentState);
        }
    }

    private boolean addBlockLine(BlockPos pos, Direction dir) {
        BlockState blockState = this.level.getBlockState(pos);
        if (blockState.isAir()) {
            return true;
        } else if (!canPush(blockState, this.level, pos, this.pushDirection, false, dir)) {
            return true;
        } else if (pos.equals(this.pistonPos)) {
            return true;
        } else if (this.toPush.contains(pos)) {
            return true;
        } else {
            int i = 1;
            if (i + this.toPush.size() > MAX_PUSH_DEPTH) {
                return false;
            } else {
                while(isSticky(blockState)) {
                    BlockPos blockPos = pos.relative(this.pushDirection.getOpposite(), i);
                    BlockState blockState2 = blockState;
                    blockState = this.level.getBlockState(blockPos);
                    if (blockState.isAir() || !canStickToEachOther(level, blockPos.relative(pushDirection), blockState2, blockPos, blockState, pushDirection.getOpposite(), pushDirection) || !canPush(blockState, this.level, blockPos, this.pushDirection, false, this.pushDirection.getOpposite()) || blockPos.equals(this.pistonPos)) {
                        break;
                    }

                    ++i;
                    if (i + this.toPush.size() > MAX_PUSH_DEPTH) {
                        return false;
                    }
                }

                int j = 0;

                for(int k = i - 1; k >= 0; --k) {
                    this.toPush.add(pos.relative(this.pushDirection.getOpposite(), k));
                    ++j;
                }

                int l = 1;

                while(true) {
                    BlockPos blockPos2 = pos.relative(this.pushDirection, l);
                    int m = this.toPush.indexOf(blockPos2);
                    if (m > -1) {
                        this.reorderListAtCollision(j, m);

                        for(int n = 0; n <= m + j; ++n) {
                            BlockPos blockPos3 = this.toPush.get(n);
                            if (isSticky(this.level.getBlockState(blockPos3)) && !this.addBranchingBlocks(blockPos3)) {
                                return false;
                            }
                        }

                        return true;
                    }

                    blockState = this.level.getBlockState(blockPos2);
                    if (blockState.isAir()) {
                        return true;
                    }

                    if (!canPush(blockState, this.level, blockPos2, this.pushDirection, true, this.pushDirection) || blockPos2.equals(this.pistonPos)) {
                        return false;
                    }

                    if (blockState.getPistonPushReaction() == PushReaction.DESTROY) {
                        this.toDestroy.add(blockPos2);
                        return true;
                    }

                    if (this.toPush.size() >= MAX_PUSH_DEPTH) {
                        return false;
                    }

                    this.toPush.add(blockPos2);
                    ++j;
                    ++l;
                }
            }
        }
    }

    private void reorderListAtCollision(int from, int to) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> list2 = Lists.newArrayList();
        List<BlockPos> list3 = Lists.newArrayList();
        list.addAll(this.toPush.subList(0, to));
        list2.addAll(this.toPush.subList(this.toPush.size() - from, this.toPush.size()));
        list3.addAll(this.toPush.subList(to, this.toPush.size() - from));
        this.toPush.clear();
        this.toPush.addAll(list);
        this.toPush.addAll(list2);
        this.toPush.addAll(list3);
    }

    private boolean addBranchingBlocks(BlockPos pos) {
        BlockState blockState = this.level.getBlockState(pos);

        for(Direction direction : Direction.values()) {
            if (direction.getAxis() != this.pushDirection.getAxis()) {
                BlockPos blockPos = pos.relative(direction);
                BlockState blockState2 = this.level.getBlockState(blockPos);
                if (canStickToEachOther(level, pos, blockState2, blockPos, blockState, direction, pushDirection) && !this.addBlockLine(blockPos, direction)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Direction getPushDirection() {
        return this.pushDirection;
    }

    public List<BlockPos> getToPush() {
        return this.toPush;
    }

    public List<BlockPos> getToDestroy() {
        return this.toDestroy;
    }
    
    public static boolean canPush(BlockState state, Level world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir) {
        if (pos.getY() < world.getMinBuildHeight() || pos.getY() > world.getMaxBuildHeight() - 1 || !world.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }
        if (state.isAir()) {
            return true;
        }
        if ((direction == Direction.DOWN && pos.getY() == world.getMinBuildHeight()) || (direction == Direction.UP && pos.getY() == world.getMaxBuildHeight() - 1)) {
            return false;
        }

        if (state.is(Blocks.PISTON) || state.is(Blocks.STICKY_PISTON)) {
            return !state.getValue(PistonBaseBlock.EXTENDED);
        }

        if (state.is(Blocks.PISTON_HEAD) || state.is(Blocks.MOVING_PISTON)) {
            return false;
        }

        return switch (state.getPistonPushReaction()) {
        case BLOCK -> true;
        case DESTROY -> canBreak;
        case PUSH_ONLY -> direction == pistonDir;
        case IGNORE, NORMAL -> true;
        default -> true;
        };
    }
}
