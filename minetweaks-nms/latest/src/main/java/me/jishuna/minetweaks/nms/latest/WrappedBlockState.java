package me.jishuna.minetweaks.nms.latest;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class WrappedBlockState extends BlockState {

    public WrappedBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> codec) {
        super(block, propertyMap, codec);
    }

    public WrappedBlockState(BlockState handle) {
        this(handle.getBlock(), handle.getValues(), getCodec(handle));
    }

    private static MapCodec<BlockState> getCodec(BlockState handle) {
        return MapCodec.of(Encoder.empty(), Decoder.unit(handle));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntityType.PISTON, CustomPistonMovingBlockEntity::tick);
    }

    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

}
