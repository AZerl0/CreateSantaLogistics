package net.liukrast.santa.ponder.scenes;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.liukrast.santa.SantaPonderPlugin;
import net.liukrast.santa.registry.SantaEntityTypes;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.entity.RoboElf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class RoboElfScene {
    public static void roboElf(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        common("robo_elf", scene, util);

        BlockPos pos = new BlockPos(1,1,1);
        SantaPonderPlugin.displayText(builder, pos, 60, true);
        SantaPonderPlugin.displayText(builder, pos, 40, false);
        SantaPonderPlugin.displayText(builder, pos, 80, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 80, false);

        scene.world().showIndependentSection(util.select().fromTo(3, 1, 0, 0, 1, 3), Direction.NORTH);
        scene.idle(20);
        SantaPonderPlugin.displayText(builder, pos, 140, true);
    }

    public static void roboElfPackaging(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        common("robo_elf_packaging", scene, util);

        var e1 = scene.world().createEntity(w -> {
            PackageEntity pack = AllEntityTypes.PACKAGE.create(w);
            Vec3 p = util.vector().topOf(util.grid().at(1,0,0));
            assert pack != null;
            pack.box = PackageStyles.getRandomBox();
            pack.setPos(p.x, p.y, p.z);
            pack.xo = p.x;
            pack.yo = p.y;
            pack.zo = p.z;
            pack.yRotO = 180;
            pack.setYRot(180);
            pack.yHeadRotO = 180;
            pack.yHeadRot = 180;
            return pack;
        });

        scene.idle(20);

        BlockPos pos = new BlockPos(1,1,1);
        SantaPonderPlugin.displayText(builder, pos, 80, true);
        scene.world().modifyEntity(e1, Entity::discard);

        var e2 = scene.world().createEntity(w -> {
            PackageEntity pack = AllEntityTypes.PACKAGE.create(w);
            Vec3 p = util.vector().topOf(util.grid().at(1,0,0));
            assert pack != null;
            pack.box = SantaItems.PRESENTS.getFirst().toStack();
            pack.setPos(p.x, p.y, p.z);
            pack.xo = p.x;
            pack.yo = p.y;
            pack.zo = p.z;
            pack.yRotO = 180;
            pack.setYRot(180);
            pack.yHeadRotO = 180;
            pack.yHeadRot = 180;
            return pack;
        });
        SantaPonderPlugin.displayText(builder, pos, 100, false);

        SantaPonderPlugin.displayText(builder, pos, 120, true);
        SantaPonderPlugin.displayText(builder, pos, 120, true);

        scene.idle(20);

        scene.world().modifyEntity(e2, Entity::discard);
    }

    public static void roboElfTrust(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        common("robo_elf_trust", scene, util);
        BlockPos pos = new BlockPos(1,1,1);
        SantaPonderPlugin.displayText(builder, pos, 80, true);
        SantaPonderPlugin.displayText(builder, pos, 60, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);
        SantaPonderPlugin.displayText(builder, pos, 100, true);
        SantaPonderPlugin.displayText(builder, pos, 100, false);
    }

    private static void common(String id, CreateSceneBuilder scene, SceneBuildingUtil util) {
        scene.title(id, "");
        scene.configureBasePlate(0, 0, 3);
        scene.scaleSceneView(0.825f);
        scene.setSceneOffsetY(-2f);
        scene.world().showIndependentSection(util.select().fromTo(3, 0, 0, 0, 0, 3), Direction.UP);
        scene.idle(10);

        scene.world().createEntity(w -> {
            RoboElf elf = SantaEntityTypes.ROBO_ELF.get().create(w);
            Vec3 p = util.vector().topOf(util.grid().at(1, 0, 1));
            assert elf != null;
            elf.setPos(p.x, p.y, p.z);
            elf.xo = p.x;
            elf.yo = p.y;
            elf.zo = p.z;
            elf.yRotO = 180;
            elf.setYRot(180);
            elf.yHeadRotO = 180;
            elf.yHeadRot = 180;
            return elf;
        });
    }
}
