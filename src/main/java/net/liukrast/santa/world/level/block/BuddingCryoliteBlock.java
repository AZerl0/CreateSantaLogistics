package net.liukrast.santa.world.level.block;

import com.mojang.serialization.MapCodec;
import net.liukrast.santa.registry.SantaBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class BuddingCryoliteBlock extends BuddingAmethystBlock {
    public static final MapCodec<BuddingAmethystBlock> CODEC = simpleCodec(BuddingCryoliteBlock::new);

    public BuddingCryoliteBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<BuddingAmethystBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = SantaBlocks.SMALL_CRYOLITE_BUD.get();
            } else if (blockstate.is(SantaBlocks.SMALL_CRYOLITE_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = SantaBlocks.MEDIUM_CRYOLITE_BUD.get();
            } else if (blockstate.is(SantaBlocks.MEDIUM_CRYOLITE_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = SantaBlocks.LARGE_CRYOLITE_BUD.get();
            } else if (blockstate.is(SantaBlocks.LARGE_CRYOLITE_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = SantaBlocks.CRYOLITE_CLUSTER.get();
            }

            if (block != null) {
                BlockState blockState = block.defaultBlockState()
                        .setValue(AmethystClusterBlock.FACING, direction)
                        .setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                level.setBlockAndUpdate(blockpos, blockState);
            }
        }
    }
}
