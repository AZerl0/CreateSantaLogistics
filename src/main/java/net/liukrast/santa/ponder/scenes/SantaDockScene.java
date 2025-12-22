package net.liukrast.santa.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.liukrast.santa.SantaPonderPlugin;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.world.level.block.SantaDockBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class SantaDockScene {

    public static void santaDock(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("santa_dock", "");
        scene.configureBasePlate(0, 0, 5);
        scene.scaleSceneView(0.825f);
        scene.setSceneOffsetY(-2f);
        scene.world().showIndependentSection(util.select().fromTo(5, 0, 0, 0, 0, 5), Direction.UP);
        scene.idle(10);

        BlockPos pos = new BlockPos(2,1,2);

        scene.world().showIndependentSection(util.select().position(pos), Direction.NORTH);

        SantaPonderPlugin.displayText(builder, pos, 90, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);

        scene.world().setBlock(pos, SantaBlocks.SANTA_DOCK.get().defaultBlockState().setValue(SantaDockBlock.STATE, SantaDockBlock.State.CONNECTED), false);
        PonderHilo.linkEffect(scene, pos);

        SantaPonderPlugin.displayText(builder, pos, 100, false);
        SantaPonderPlugin.displayText(builder, pos, 80, false);


        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);

    }
}
