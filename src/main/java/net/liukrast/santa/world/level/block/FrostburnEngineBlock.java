package net.liukrast.santa.world.level.block;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.liukrast.multipart.block.AbstractMultipartBlock;
import net.liukrast.santa.DeployerGoggleInformation;
import net.liukrast.santa.registry.SantaBlockEntityTypes;
import net.liukrast.santa.world.level.block.entity.FrostburnEngineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class FrostburnEngineBlock extends AbstractMultipartBlock implements IRotate, IBE<FrostburnEngineBlockEntity>, DeployerGoggleInformation {
    public FrostburnEngineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        // onBlockAdded is useless for init, as sometimes the BE gets re-instantiated

        // however, if a block change occurs that does not change kinetic connections,
        // we can prevent a major re-propagation here

        BlockEntity blockEntity = worldIn.getBlockEntity(pos);
        if (blockEntity instanceof KineticBlockEntity kineticBlockEntity) {
            kineticBlockEntity.preventSpeedUpdate = 0;

            if (oldState.getBlock() != state.getBlock())
                return;
            if (state.hasBlockEntity() != oldState.hasBlockEntity())
                return;
            if (!areStatesKineticallyEquivalent(oldState, state))
                return;

            kineticBlockEntity.preventSpeedUpdate = 2;
        }
    }

    protected boolean areStatesKineticallyEquivalent(BlockState oldState, BlockState newState) {
        if (oldState.getBlock() != newState.getBlock())
            return false;
        return getRotationAxis(newState) == getRotationAxis(oldState);
    }

    @Override
    public void defineParts(Builder builder) {
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for(int z = 0; z < 3; z++) {
                    builder.define(x,y,z);
                }
            }
        }
        builder.define(-1, 2, 0);
        builder.define(-1, 2, 2);
        builder.define(3, 2, 0);
        builder.define(3, 2, 2);

        builder.define(0, 2, -1);
        builder.define(2, 2, -1);
        builder.define(0, 2, 3);
        builder.define(2, 2, 3);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return state.getValue(getPartsProperty()) == 10 && face == Direction.DOWN;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<FrostburnEngineBlockEntity> getBlockEntityClass() {
        return FrostburnEngineBlockEntity.class;
    }

    @Override
    @Nullable
    public BlockEntityType<? extends FrostburnEngineBlockEntity> getBlockEntityType() {
        return SantaBlockEntityTypes.FROSTBURN_ENGINE.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(getPartsProperty()) == 10 ? IBE.super.newBlockEntity(pos, state) : null;
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }

    @Deprecated
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(newState.is(state.getBlock()) || movedByPiston) {
            super.onRemove(state, level, pos, newState, movedByPiston);
            return;
        }
        destroy(level, pos, state);
    }
}
