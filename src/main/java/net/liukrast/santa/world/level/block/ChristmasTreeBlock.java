package net.liukrast.santa.world.level.block;

import net.liukrast.multipart.block.AbstractMultipartBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class ChristmasTreeBlock extends AbstractMultipartBlock {
    private static final VoxelShape SHAPE_0 = Shapes.or(
            box(6, 0, 6, 10, 16, 10),
            box(0, 2, 0, 16, 9, 16),
            box(1, 9, 1, 15, 16, 15)
    );
    private static final VoxelShape SHAPE_1 = Shapes.or(
            box(3, 0, 3, 13,7, 13),
            box(4,7,4,12,14,12)
    );

    public ChristmasTreeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void defineParts(Builder builder) {
        builder.define(0,0,0);
        builder.define(0, 1, 0);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int part = state.getValue(getPartsProperty());
        return part == 0 ? SHAPE_0 : SHAPE_1;
    }
}
