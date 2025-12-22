package net.liukrast.santa.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.liukrast.santa.SantaPonderPlugin;
import net.liukrast.santa.registry.SantaEntityTypes;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.entity.RoboElf;
import net.liukrast.santa.world.entity.SantaClaus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class SantaClausPonderScene {
    public static void santaClaus(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("santa_claus", "");
        scene.configureBasePlate(0, 0, 5);
        scene.scaleSceneView(0.825f);
        scene.setSceneOffsetY(-2f);
        scene.world().showIndependentSection(util.select().fromTo(5, 0, 0, 0, 0, 5), Direction.UP);
        scene.idle(10);

        var e1 = scene.world().createEntity(w -> {
            SantaClaus santa = SantaEntityTypes.SANTA_CLAUS.get().create(w);
            Vec3 p = util.vector().topOf(util.grid().at(2,0,2));
            assert santa != null;
            santa.setAnimationState(SantaClaus.State.SLEEPING);
            santa.setPos(p.x, p.y, p.z);
            santa.xo = p.x;
            santa.yo = p.y;
            santa.zo = p.z;
            santa.yRotO = 180;
            santa.setYRot(180);
            santa.yHeadRotO = 180;
            santa.yHeadRot = 180;
            return santa;
        });

        BlockPos pos = new BlockPos(1,3,1);

        SantaPonderPlugin.displayText(builder, pos, 60, true);

        var e2 = scene.world().showIndependentSection(util.select().fromTo(5, 1, 0, 0, 3, 5), Direction.NORTH);
        SantaPonderPlugin.displayText(builder, new BlockPos(3, 2, 0), 80, false);

        scene.world().hideIndependentSection(e2, Direction.SOUTH);
        scene.idle(20);


        scene.world().modifyEntity(e1, e -> ((SantaClaus)e).setAnimationState(SantaClaus.State.IDLE));
        SantaPonderPlugin.displayText(builder, pos, 80, true);
        SantaPonderPlugin.displayText(builder, pos, 40, false);
        scene.idle(20);


        SantaPonderPlugin.displayText(builder, pos, 160, true);
        scene.world().modifyEntity(e1, e -> ((SantaClaus)e).setAnimationState(SantaClaus.State.CURIOUS));
        SantaPonderPlugin.displayText(builder, pos, 120, false);
        scene.world().modifyEntity(e1, e -> {
            ((SantaClaus)e).setItemInHand(InteractionHand.MAIN_HAND, Items.COOKIE.getDefaultInstance());
            ((SantaClaus)e).setAnimationState(SantaClaus.State.EATING);
        });
        scene.idle(40);
        scene.world().modifyEntity(e1, e -> {
            ((SantaClaus)e).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            ((SantaClaus)e).setAnimationState(SantaClaus.State.CRAFTING);
        });
        SantaPonderPlugin.displayText(builder, pos, 100, false);

        scene.idle(10);
        scene.world().modifyEntity(e1, e -> {
            ((SantaClaus)e).setItemInHand(InteractionHand.MAIN_HAND, SantaItems.FROSTBURN_CORE.toStack());
            ((SantaClaus)e).setAnimationState(SantaClaus.State.IDLE);
        });
        scene.idle(20);
        SantaPonderPlugin.displayText(builder, pos, 80, false);

    }
}
