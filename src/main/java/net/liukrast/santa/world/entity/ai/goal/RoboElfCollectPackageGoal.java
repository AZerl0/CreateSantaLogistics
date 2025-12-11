package net.liukrast.santa.world.entity.ai.goal;

import com.simibubi.create.content.logistics.box.PackageEntity;
import net.liukrast.santa.world.entity.RoboElf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class RoboElfCollectPackageGoal extends MeleeAttackGoal {
    public RoboElfCollectPackageGoal(RoboElf mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.getMainHandItem().isEmpty();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if(!(target instanceof PackageEntity pe)) return;
        if(this.canPerformAttack(target)) {
            this.resetAttackCooldown();
            this.mob.setItemInHand(InteractionHand.MAIN_HAND, pe.box);
            pe.discard();
            stop();
        }
    }
}
