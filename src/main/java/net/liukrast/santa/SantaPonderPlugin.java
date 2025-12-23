package net.liukrast.santa;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.liukrast.santa.datagen.SantaPonderTagProvider;
import net.liukrast.santa.ponder.scenes.FrostburnEngineScene;
import net.liukrast.santa.ponder.scenes.RoboElfScene;
import net.liukrast.santa.ponder.scenes.SantaClausPonderScene;
import net.liukrast.santa.ponder.scenes.SantaDockScene;
import net.liukrast.santa.registry.SantaBlocks;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class SantaPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return SantaConstants.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<Item> HELPER = helper.withKeyFunction(BuiltInRegistries.ITEM::getKey);

        HELPER.forComponents(SantaBlocks.SANTA_DOCK.asItem())
                .addStoryBoard("high_logistics/santa_dock", SantaDockScene::santaDock);
        HELPER.forComponents(SantaBlocks.FROSTBURN_ENGINE.asItem())
                .addStoryBoard("frostburn_engine", FrostburnEngineScene::frostburnEngine);
        HELPER.forComponents(SantaItems.ROBO_ELF_SPAWN_EGG.asItem())
                .addStoryBoard("robo_elf", RoboElfScene::roboElf)
                .addStoryBoard("robo_elf_packaging", RoboElfScene::roboElfPackaging)
                .addStoryBoard("robo_elf_trust", RoboElfScene::roboElfTrust);

        HELPER.forComponents(SantaItems.SANTA_CLAUS_SPAWN_EGG.asItem())
                .addStoryBoard("santa_claus", SantaClausPonderScene::santaClaus);
    }

    public static void displayText(SceneBuilder builder, BlockPos pos, int time, boolean keyframe) {
        var overlay = builder.overlay()
                .showText(time)
                .text("")
                .placeNearTarget()
                .pointAt(pos.getCenter().add(-0.25f, 0.25f,0));
        if(keyframe) overlay.attachKeyFrame();
        builder.idle(time+20);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        SantaPonderTagProvider.register(helper);
    }
}
