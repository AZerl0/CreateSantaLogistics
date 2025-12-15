package net.liukrast.santa.world.entity;

import net.liukrast.santa.world.entity.ai.goal.SantaClausCollectFoodGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class SantaClaus extends PathfinderMob {
    public SantaClaus(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createSantaAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 2000)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SantaClausCollectFoodGoal(this));
    }
}
