package net.liukrast.santa.world.level.block;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.liukrast.santa.registry.SantaBlockEntityTypes;
import net.liukrast.santa.world.level.block.entity.ElfChargeStationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Objects;

public class ElfChargeStationBlock extends HorizontalKineticBlock implements IBE<ElfChargeStationBlockEntity> {
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    public ElfChargeStationBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(OCCUPIED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = getPreferredHorizontalFacing(context);
        return defaultBlockState()
                .setValue(HORIZONTAL_FACING, Objects
                        .requireNonNullElseGet(preferred, context::getHorizontalDirection)
                        .getOpposite()
                );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OCCUPIED);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING)
                .getOpposite();
    }

    @Override
    public Class<ElfChargeStationBlockEntity> getBlockEntityClass() {
        return ElfChargeStationBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElfChargeStationBlockEntity> getBlockEntityType() {
        return SantaBlockEntityTypes.ELF_CHARGE_STATION.get();
    }
}
