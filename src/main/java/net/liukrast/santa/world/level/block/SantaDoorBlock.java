package net.liukrast.santa.world.level.block;

import net.liukrast.multipart.block.AbstractFacingMultipartBlock;
import net.liukrast.santa.world.level.block.entity.SantaDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.concurrent.atomic.AtomicBoolean;

@NonnullDefault
public class SantaDoorBlock extends AbstractFacingMultipartBlock implements EntityBlock, LiquidBlockContainer {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

    public SantaDoorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LOCKED, false).setValue(POWERED, false).setValue(OPEN, false));
    }

    public int getWidth() {
        return 4;
    }

    @Override
    public void defineParts(Builder builder) {
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < 4; y++) {
                builder.define(x, y, 0);
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(OPEN) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN, LOCKED, POWERED);
    }

    public boolean canUnlock(ItemStack stack) {
        return false;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(LOCKED) && canUnlock(stack)) {
            state = state.cycle(LOCKED).setValue(OPEN, true);
            stack.consume(1, player);
            //TODO: Playsound!!
        } else {
            if(state.getValue(LOCKED) && !player.getAbilities().instabuild) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            state = state.cycle(OPEN);
            level.gameEvent(player, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
        level.setBlock(pos, state, 10);
        // Playsound?
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(getPartsProperty()) == 0 ? new SantaDoorBlockEntity(pos, state) : null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return state.getValue(LOCKED) ? -1.0f : super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return state.getValue(LOCKED) ? 3600000.0f : super.getExplosionResistance(state, level, pos, explosion);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(state.getValue(FACING).getAxis() == Direction.Axis.X) return box(5,0,0, 11,16,16);
        else return box(0, 0, 5, 16, 16, 11);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if(state.getValue(LOCKED)) return;
        AtomicBoolean flag = new AtomicBoolean(false);
        forEachElement(pos, state, pos1 -> flag.set(flag.get() | level.hasNeighborSignal(pos1)));
        if(this == neighborBlock || flag.get() == state.getValue(POWERED)) return;
        if(flag.get() != state.getValue(OPEN)) {
            // Play sound??
            level.gameEvent(null, flag.get() ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
        level.setBlock(pos, state.setValue(POWERED, flag.get()).setValue(OPEN, flag.get()), 2);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if(!neighborState.is(this)) return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        return neighborState.setValue(FACING, state.getValue(FACING)).setValue(getPartsProperty(), state.getValue(getPartsProperty()));
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return switch (pathComputationType) {
            case LAND, AIR -> state.getValue(OPEN);
            case WATER -> false;
        };
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }
}
