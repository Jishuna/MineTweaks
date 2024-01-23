package me.jishuna.minetweaks.nms.latest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R3.block.data.CraftBlockData;
import org.bukkit.event.block.BlockPistonRetractEvent;
import me.jishuna.minetweaks.nms.NMSAdapter;

// TODO clean up entire class
public class NMSAdapterImpl implements NMSAdapter {

    @Override
    public void activatePiston(Location location, BlockData data) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        BlockState state = ((CraftBlockData) data).getState();

        checkIfExtend(level, pos, state);
    }

    public boolean getNeighborSignal(SignalGetter world, BlockPos pos, Direction pistonFace) {
        Direction[] aenumdirection = Direction.values();
        int i = aenumdirection.length;

        int j;

        for (j = 0; j < i; ++j) {
            Direction enumdirection1 = aenumdirection[j];

            if (enumdirection1 != pistonFace && world.hasSignal(pos.relative(enumdirection1), enumdirection1)) {
                return true;
            }
        }

        if (world.hasSignal(pos, Direction.DOWN)) {
            return true;
        }
        BlockPos blockposition1 = pos.above();
        Direction[] aenumdirection1 = Direction.values();

        j = aenumdirection1.length;

        for (int k = 0; k < j; ++k) {
            Direction enumdirection2 = aenumdirection1[k];

            if (enumdirection2 != Direction.DOWN && world.hasSignal(blockposition1.relative(enumdirection2), enumdirection2)) {
                return true;
            }
        }

        return false;
    }

    private void checkIfExtend(Level world, BlockPos pos, BlockState state) {
        boolean sticky = state.is(Blocks.STICKY_PISTON);
        Direction enumdirection = state.getValue(PistonBaseBlock.FACING);
        boolean flag = getNeighborSignal(world, pos, enumdirection);

        if (flag && !(Boolean) state.getValue(PistonBaseBlock.EXTENDED)) {
            triggerEvent(state, world, pos, 0, enumdirection.get3DDataValue(), sticky);
        } else if (!flag && state.getValue(PistonBaseBlock.EXTENDED)) {
            BlockPos blockposition1 = pos.relative(enumdirection, 2);
            BlockState iblockdata1 = world.getBlockState(blockposition1);
            byte b0 = 1;

            if (iblockdata1.is(Blocks.MOVING_PISTON) && iblockdata1.getValue(PistonBaseBlock.FACING) == enumdirection) {
                BlockEntity tileentity = world.getBlockEntity(blockposition1);

                if (tileentity instanceof PistonMovingBlockEntity tileentitypiston) {
                    if (tileentitypiston.isExtending() && (tileentitypiston.getProgress(0.0F) < 0.5F || world.getGameTime() == tileentitypiston.getLastTicked() || ((ServerLevel) world).isHandlingTick())) {
                        b0 = 2;
                    }
                }
            }

            if (sticky) {
                org.bukkit.block.Block block = world.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
                BlockPistonRetractEvent event = new BlockPistonRetractEvent(block, ImmutableList.<org.bukkit.block.Block>of(), CraftBlock.notchToBlockFace(enumdirection));
                world.getCraftServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    return;
                }
            }
            triggerEvent(state, world, pos, b0, enumdirection.get3DDataValue(), sticky);
        }

    }

    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int type, int data, boolean sticky) {
        Direction enumdirection = state.getValue(PistonBaseBlock.FACING);
        BlockState iblockdata1 = state.setValue(PistonBaseBlock.EXTENDED, true);

        if (!world.isClientSide) {
            boolean flag = getNeighborSignal(world, pos, enumdirection);

            if (flag && (type == 1 || type == 2)) {
                world.setBlock(pos, iblockdata1, 2);
                return false;
            }

            if (!flag && type == 0) {
                return false;
            }
        }

        if (type == 0) {
            if (!moveBlocks(world, pos, enumdirection, true, sticky)) {
                return false;
            }

            MinecraftServer.getServer().getPlayerList().broadcast((Player) null, pos.getX(), pos.getY(), pos.getZ(), 64.0D, world.dimension(), new ClientboundBlockEventPacket(pos, state.getBlock(), type, data));
            world.setBlock(pos, iblockdata1, 67);
            world.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
            world.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(iblockdata1));
        } else if (type == 1 || type == 2) {
            BlockEntity tileentity = world.getBlockEntity(pos.relative(enumdirection));

            if (tileentity instanceof PistonMovingBlockEntity) {
                ((PistonMovingBlockEntity) tileentity).finalTick();
            }

            BlockState iblockdata2 = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, enumdirection).setValue(MovingPistonBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);

            world.setBlock(pos, iblockdata2, 20);
            world.setBlockEntity(new CustomPistonMovingBlockEntity(pos, new WrappedBlockState(iblockdata2), state.getBlock().defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.from3DDataValue(data & 7)), enumdirection, false, true, null)); // Paper - diff on change
            world.blockUpdated(pos, iblockdata2.getBlock());
            iblockdata2.updateNeighbourShapes(world, pos, 2);
            if (sticky) {
                BlockPos blockposition1 = pos.offset(enumdirection.getStepX() * 2, enumdirection.getStepY() * 2, enumdirection.getStepZ() * 2);
                BlockState iblockdata3 = world.getBlockState(blockposition1);
                boolean flag1 = false;

                if (iblockdata3.is(Blocks.MOVING_PISTON)) {
                    BlockEntity tileentity1 = world.getBlockEntity(blockposition1);

                    if ((tileentity1 instanceof PistonMovingBlockEntity tileentitypiston) && (tileentitypiston.getDirection() == enumdirection && tileentitypiston.isExtending())) {
                        tileentitypiston.finalTick();
                        flag1 = true;
                    }
                }

                if (!flag1) {
                    if (type == 1 && !iblockdata3.isAir() && CustomPistonStructureResolver.canPush(iblockdata3, world, blockposition1, enumdirection.getOpposite(), false, enumdirection) && (iblockdata3.getPistonPushReaction() == PushReaction.NORMAL || iblockdata3.is(Blocks.PISTON) || iblockdata3.is(Blocks.STICKY_PISTON))) {
                        moveBlocks(world, pos, enumdirection, false, sticky);
                        MinecraftServer.getServer().getPlayerList().broadcast((Player) null, pos.getX(), pos.getY(), pos.getZ(), 64.0D, world.dimension(), new ClientboundBlockEventPacket(pos, state.getBlock(), type, data));
                    } else {
                        world.removeBlock(pos.relative(enumdirection), false);
                        MinecraftServer.getServer().getPlayerList().broadcast((Player) null, pos.getX(), pos.getY(), pos.getZ(), 64.0D, world.dimension(), new ClientboundBlockEventPacket(pos, state.getBlock(), type, data));
                    }
                }
            } else {
                world.removeBlock(pos.relative(enumdirection), false);
            }

            world.playSound((Player) null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
            world.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(iblockdata2));
        }

        return true;
    }

    private boolean moveBlocks(Level world, BlockPos pos, Direction dir, boolean retract, boolean sticky) {
        BlockPos blockposition1 = pos.relative(dir);

        if (!retract && world.getBlockState(blockposition1).is(Blocks.PISTON_HEAD)) {
            world.setBlock(blockposition1, Blocks.AIR.defaultBlockState(), 20);
        }

        CustomPistonStructureResolver pistonextendschecker = new CustomPistonStructureResolver(world, pos, dir, retract);

        if (!pistonextendschecker.resolve()) {
            return false;
        }
        Map<BlockPos, BlockState> map = Maps.newHashMap();
        List<BlockPos> list = pistonextendschecker.getToPush();
        List<BlockState> list1 = Lists.newArrayList();
        for (BlockPos blockposition2 : list) {
            BlockState iblockdata = world.getBlockState(blockposition2);

            list1.add(iblockdata);
            map.put(blockposition2, iblockdata);
        }

        List<BlockPos> list2 = pistonextendschecker.getToDestroy();
        BlockState[] aiblockdata = new BlockState[list.size() + list2.size()];
        Direction enumdirection1 = retract ? dir : dir.getOpposite();
        int i = 0;
        BlockPos blockposition3;
        int j;
        BlockState iblockdata1;

        for (j = list2.size() - 1; j >= 0; --j) {
            blockposition3 = list2.get(j);
            iblockdata1 = world.getBlockState(blockposition3);
            if (iblockdata1.hasBlockEntity()) {
                world.getBlockEntity(blockposition3);
            }

            // dropResources(iblockdata1, world, blockposition3, tileentity); // Paper
            world.setBlock(blockposition3, Blocks.AIR.defaultBlockState(), 18);
            world.gameEvent(GameEvent.BLOCK_DESTROY, blockposition3, GameEvent.Context.of(iblockdata1));
            if (!iblockdata1.is(BlockTags.FIRE)) {
                world.addDestroyBlockEffect(blockposition3, iblockdata1);
            }

            aiblockdata[i++] = iblockdata1;
        }

        for (j = list.size() - 1; j >= 0; --j) {
            blockposition3 = list.get(j);
            BlockEntity blockEntity = null;
            BlockState state = list1.get(j);
            if (state.hasBlockEntity()) {
                blockEntity = world.getBlockEntity(blockposition3);
                world.removeBlockEntity(blockposition3);

            }

            iblockdata1 = world.getBlockState(blockposition3);
            blockposition3 = blockposition3.relative(enumdirection1);
            map.remove(blockposition3);
            BlockState iblockdata2 = Blocks.MOVING_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, dir);

            world.setBlock(blockposition3, iblockdata2, 68);
            world.setBlockEntity(new CustomPistonMovingBlockEntity(blockposition3, new WrappedBlockState(iblockdata2), state, dir, retract, false, blockEntity));
            aiblockdata[i++] = iblockdata1;
        }

        if (retract) {
            PistonType blockpropertypistontype = sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState iblockdata3 = Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, dir).setValue(PistonHeadBlock.TYPE, blockpropertypistontype);

            iblockdata1 = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, dir).setValue(MovingPistonBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);
            map.remove(blockposition1);
            world.setBlock(blockposition1, iblockdata1, 68);
            world.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(blockposition1, iblockdata1, iblockdata3, dir, true, true));
        }

        BlockState iblockdata4 = Blocks.AIR.defaultBlockState();
        Iterator iterator1 = map.keySet().iterator();

        while (iterator1.hasNext()) {
            BlockPos blockposition4 = (BlockPos) iterator1.next();

            world.setBlock(blockposition4, iblockdata4, 82);
        }

        iterator1 = map.entrySet().iterator();

        BlockPos blockposition5;

        while (iterator1.hasNext()) {
            Entry<BlockPos, BlockState> entry = (Entry) iterator1.next();

            blockposition5 = entry.getKey();
            BlockState iblockdata5 = entry.getValue();

            iblockdata5.updateIndirectNeighbourShapes(world, blockposition5, 2);
            iblockdata4.updateNeighbourShapes(world, blockposition5, 2);
            iblockdata4.updateIndirectNeighbourShapes(world, blockposition5, 2);
        }

        i = 0;

        int k;

        for (k = list2.size() - 1; k >= 0; --k) {
            iblockdata1 = aiblockdata[i++];
            blockposition5 = list2.get(k);
            iblockdata1.updateIndirectNeighbourShapes(world, blockposition5, 2);
            world.updateNeighborsAt(blockposition5, iblockdata1.getBlock());
        }

        for (k = list.size() - 1; k >= 0; --k) {
            world.updateNeighborsAt(list.get(k), aiblockdata[i++].getBlock());
        }

        if (retract) {
            world.updateNeighborsAt(blockposition1, Blocks.PISTON_HEAD);
        }

        return true;
    }
}
