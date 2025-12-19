package net.liukrast.santa.world.entity.ai.goal;

import net.createmod.catnip.math.VecHelper;
import net.liukrast.santa.registry.SantaItems;
import net.liukrast.santa.world.entity.RoboElf;
import net.liukrast.santa.world.entity.SantaClaus;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SantaClausCraftGoal extends Goal {

    protected final SantaClaus mob;
    private int cooldown = 0;
    private int secondCooldown = 0;
    private boolean sat;
    private ItemStack stack;

    public SantaClausCraftGoal(SantaClaus mob) {
        this.mob = mob;
    }


    @Override
    public boolean canUse() {
        return cooldown > 0 || secondCooldown > 0 || (mob.isSatisfiedA() || mob.isSatisfiedB() && mob.getMainHandItem().isEmpty());
    }

    @Override
    public void start() {
        this.mob.setAnimationState(SantaClaus.State.CRAFTING);
        cooldown = 100;
        sat = mob.isSatisfiedA();
        stack = new ItemStack(sat ? SantaItems.FROSTBURN_CORE.get() : SantaItems.SANTA_KEY.get());
    }

    @Override
    public void tick() {
        if(cooldown > 0) {
            cooldown--;
            Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), mob.getRandom(), .125f);
            ((ServerLevel)mob.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), mob.getX(), mob.getY(), mob.getZ(), 10, m.x, m.y, m.z, 0.1);
        } else {
            mob.setItemInHand(InteractionHand.MAIN_HAND, stack);
            if(sat) mob.setSatisfactionA(0);
            else mob.setSatisfactionB(0);
            secondCooldown = 20;
        }
        if(secondCooldown > 0) {
            secondCooldown--;
        } else {
            mob.spawnAtLocation(mob.getMainHandItem());
            mob.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
    }

    @Override
    public void stop() {
        mob.setAnimationState(SantaClaus.State.IDLE);
    }
}
