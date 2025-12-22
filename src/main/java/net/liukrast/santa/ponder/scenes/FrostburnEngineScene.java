package net.liukrast.santa.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.liukrast.santa.SantaPonderPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class FrostburnEngineScene {
    public static void frostburnEngine(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("frostburn_engine", "");
        scene.configureBasePlate(0, 0, 7);
        scene.scaleSceneView(0.825f);
        scene.setSceneOffsetY(-2f);
        scene.world().showIndependentSection(util.select().fromTo(7, 0, 0, 0, 0, 7), Direction.UP);
        scene.idle(10);
        scene.world().showIndependentSection(util.select().fromTo(7, 1, 0, 0, 1, 7), Direction.DOWN);
        scene.idle(10);
        scene.world().showIndependentSection(util.select().fromTo(7, 2, 0, 0, 4, 7), Direction.DOWN);

        scene.idle(20);
        BlockPos pos = new BlockPos(3, 3, 3);
        SantaPonderPlugin.displayText(builder, pos, 120, true);
        SantaPonderPlugin.displayText(builder, pos, 40, false);
        SantaPonderPlugin.displayText(builder, pos, 40, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);

        SantaPonderPlugin.displayText(builder, pos, 50, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);


        BlockPos pos1 = new BlockPos(2, 6, 1);
        scene.world().showIndependentSection(util.select().fromTo(7, 5, 0, 0, 6, 7), Direction.DOWN);
        SantaPonderPlugin.displayText(builder, pos1, 60, true);
        SantaPonderPlugin.displayText(builder, pos1, 100, false);
    }
}
