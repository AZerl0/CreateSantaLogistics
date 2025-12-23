package net.liukrast.santa.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.liukrast.santa.Node;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.client.model.CogDrivenCourierModel;
import net.liukrast.santa.client.model.RudolfModel;
import net.liukrast.santa.client.model.SantaClausModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class SleighRenderer {
    private static final ResourceLocation TEXTURE = SantaConstants.id("textures/entity/cog_driven_courier.png");
    private static final ResourceLocation RUDOLF_TEXTURE = SantaConstants.id("textures/entity/rudolf.png");
    private static final CogDrivenCourierModel MODEL = new CogDrivenCourierModel(CogDrivenCourierModel.createBodyLayer().bakeRoot());
    private static final RudolfModel RUDOLF = new RudolfModel(RudolfModel.createBodyLayer().bakeRoot());
    private static final SantaClausModel SANTA = new SantaClausModel(SantaClausModel.createBodyLayer().bakeRoot());

    @Nullable
    public static BlockPos ORIGIN = null;
    @Nullable
    public static List<Node> DOCKS = null;
    private SleighRenderer() {}

    private static final List<Boolean> DISAPPEARED = NonNullList.withSize(4, false);

    private static void render(float smoothDayTime, PoseStack poseStack, MultiConsumer tri) {
        if (ORIGIN == null) return;
        float yaw;
        float pitch = 0;
        boolean walk = true;
        boolean day = false;
        if(DOCKS == null || DOCKS.size() < 3) {
            poseStack.translate(-ORIGIN.getX(), -ORIGIN.getY(), ORIGIN.getZ());
            yaw = (float) Math.PI/2;
            walk = false;
            day = true;
        } else if(smoothDayTime < SantaConstants.NIGHT_START || smoothDayTime > SantaConstants.NIGHT_END) {
            if(smoothDayTime < SantaConstants.NIGHT_START && smoothDayTime>SantaConstants.NIGHT_START-SantaConstants.LEAVE_DURATION) {
                int start = SantaConstants.NIGHT_START-SantaConstants.LEAVE_DURATION;
                float x = (smoothDayTime-(start))/SantaConstants.LEAVE_DURATION;
                float progress = x < 0.5 ? 2*x*x : 1-2*(x-1)*(x-1);
                pitch = (float) ((x < 0.5 ? -4*x : 4*(x-1))/Math.PI/2);
                poseStack.translate(-(1-Math.pow(1-x,2))*SantaConstants.EXIT_LENGTH-ORIGIN.getX(), -ORIGIN.getY()-(SantaConstants.EXIT_HEIGTH*progress), ORIGIN.getZ());
            } else if(smoothDayTime>SantaConstants.NIGHT_END && smoothDayTime < SantaConstants.NIGHT_END+SantaConstants.LEAVE_DURATION) {
                int start = SantaConstants.NIGHT_END;
                float x = (smoothDayTime-(start))/SantaConstants.LEAVE_DURATION;
                float progress = x < 0.5 ? 2*x*x : 1-2*(x-1)*(x-1);
                pitch = (float) ((x < 0.5 ? 4*x : -4*(x-1))/Math.PI/2);
                poseStack.translate((1-progress)*SantaConstants.EXIT_LENGTH-ORIGIN.getX(), -ORIGIN.getY()-(SantaConstants.EXIT_HEIGTH*(1-progress)), ORIGIN.getZ());
            } else {
                day = true;
                poseStack.translate(-ORIGIN.getX() + 0.5, -ORIGIN.getY(), ORIGIN.getZ() + 0.5);
            }
            yaw = (float) Math.PI/2;
        } else {
            long nightStart = SantaConstants.NIGHT_START;
            long nightEnd = SantaConstants.NIGHT_END;
            float nightLength = nightEnd - nightStart;
            int steps = DOCKS.size() - 1;

            float t = smoothDayTime - nightStart;
            if (t < 0) t = 0;
            if (t > nightLength) t = (long) nightLength;

            float progress = t / nightLength;
            float interval = 1f / steps;
            int index = Math.min((int) (progress / interval), steps - 1);

            float intervalStart = interval * index;
            float polyProgress = (progress - intervalStart) / interval;//(float) (1-Math.pow(x-1, 2));

            Node current = DOCKS.get(index);
            Node next = DOCKS.get(index+1);


            Vec2 b = bezier(current, next, polyProgress);
            yaw = angle(current, next, polyProgress);
            poseStack.translate(-b.x, -ORIGIN.getY()-SantaConstants.EXIT_HEIGTH, b.y);
        }

        tri.apply(pitch, yaw, walk, day, ORIGIN);
    }

    private interface MultiConsumer {
        void apply(float pitch, float yaw, boolean walk, boolean rudolfRender, BlockPos pos);
    }

    public static void renderSleigh(float smoothDayTime, PoseStack poseStack, MultiBufferSource bufferSource) {
        poseStack.pushPose();
        render(smoothDayTime, poseStack, (pitch, yaw, walk, day, pos) -> {
            if(day != DISAPPEARED.getFirst()) {
                var level = Minecraft.getInstance().level;
                DISAPPEARED.set(0, day);
                assert level != null;
                for(int i = 0; i < 100; i++) level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX()-1 + level.getRandom().nextFloat()*5, pos.getY()+1, pos.getZ()-1 + level.getRandom().nextFloat()*5, 1, 2, 1);
            }
            if(day) return;
            MODEL.setupAnim(pitch,yaw, smoothDayTime, walk);
            int packedLight = LightTexture.pack(15, 15);
            MODEL.renderToBuffer(
                    poseStack,
                    bufferSource.getBuffer(MODEL.renderType(TEXTURE)),
                    packedLight,
                    OverlayTexture.NO_OVERLAY
            );
            SANTA.setupAnimForSleigh(pitch, yaw, smoothDayTime);
            SANTA.renderToBuffer(
                    poseStack,
                    bufferSource.getBuffer(MODEL.renderType(SantaClausRenderer.TEXTURE)),
                    packedLight,
                    OverlayTexture.NO_OVERLAY
            );

        });
        poseStack.popPose();
    }

    public static void renderRudolf(float smoothDayTime, PoseStack poseStack, MultiBufferSource bufferSource, int index) {
        poseStack.pushPose();
        render(smoothDayTime, poseStack, (pitch, yaw, walk, day, pos) -> {
            if(day != DISAPPEARED.get(index+1)) {
                var level = Minecraft.getInstance().level;
                DISAPPEARED.set(index+1, day);
                assert level != null;
                for(int i = 0; i < 40; i++) level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX()-1 + level.getRandom().nextFloat()*3, pos.getY()+1, pos.getZ()-1 + level.getRandom().nextFloat()*3, 1, 2, 1);
            }
            if(day) return;
            RUDOLF.setupAnim(pitch,yaw, smoothDayTime, walk);
            int packedLight = LightTexture.pack(15, 15);
            RUDOLF.renderToBuffer(
                    poseStack,
                    bufferSource.getBuffer(MODEL.renderType(RUDOLF_TEXTURE)),
                    packedLight,
                    OverlayTexture.NO_OVERLAY
            );
        });
        poseStack.popPose();
    }

    private static Vec2 bezier(Node a, Node b, float t) {
        return new Vec2(
                bezier(a.pos().x, a.pos().x+a.direction().x, b.pos().x-b.direction().x, b.pos().x, t),
                bezier(a.pos().y, a.pos().y+a.direction().y, b.pos().y-b.direction().y, b.pos().y, t)
        );
    }

    private static float bezier(float a, float b, float c, float d, float t) {
        return (float) (Math.pow(1-t, 3)*a + 3*Math.pow(1-t, 2)*t*b + 3*(1-t)*t*t*c+t*t*t*d);
    }

    private static float angle(Node a, Node b, float t) {
        float u = 1 - t;
        var p0 = a.pos();
        var p1 = p0.add(a.direction());
        var p3 = b.pos();
        var p2 = p3.add(b.direction().scale(-1));
        Vec2 v0 = p1.add(p0.scale(-1)).scale(3 * u * u);
        Vec2 v1 = p2.add(p1.scale(-1)).scale(6 * u * t);
        Vec2 v2 = p3.add(p2.scale(-1)).scale(3 * t * t);
        var tan = v0.add(v1).add(v2);
        return (float) (Math.atan2(tan.y, tan.x) + Math.PI/2);
    }


}
