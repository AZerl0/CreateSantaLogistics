package net.liukrast.santa.world.entity.ai.goal;

import net.createmod.catnip.math.VecHelper;
import net.liukrast.santa.world.entity.SantaClaus;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SantaClausEatGoal extends Goal {
    private int cooldown;
    private final SantaClaus santa;
    private boolean canContinueUse;

    public SantaClausEatGoal(SantaClaus santa) {
        this.santa = santa;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        var stack = santa.getMainHandItem();
        return santa.isTypeAFood(stack) || santa.isTypeBFood(stack);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && canContinueUse;
    }

    @Override
    public void start() {
        santa.setAnimationState(SantaClaus.State.EATING);
        cooldown = 20;
        canContinueUse = true;
    }

    @Override
    public void stop() {
        santa.setAnimationState(SantaClaus.State.IDLE);
    }

    @Override
    public void tick() {
        if(cooldown > 0) {
            cooldown--;
            Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), santa.getRandom(), .125f);
            ((ServerLevel)santa.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, santa.getMainHandItem()), santa.getX(), santa.getY() + 2, santa.getZ(), 10, m.x, m.y, m.z, 0.1);
        } else {
            var stack = santa.getMainHandItem();
            boolean a = santa.isTypeAFood(stack);
            if(!a && !santa.isTypeBFood(stack)) return;
            santa.incrementSatisfaction(1, a);
            santa.getMainHandItem().consume(1, santa);
            canContinueUse = false;
        }
    }
}
