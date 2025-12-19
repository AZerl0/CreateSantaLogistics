package net.liukrast.santa.world.entity;

import com.simibubi.create.content.kinetics.base.IRotate;
import net.createmod.catnip.lang.LangNumberFormat;
import net.liukrast.santa.DeployerGoggleInformation;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.SantaLang;
import net.liukrast.santa.registry.SantaAttachmentTypes;
import net.liukrast.santa.registry.SantaTags;
import net.liukrast.santa.world.entity.ai.goal.SantaClausCollectFoodGoal;
import net.liukrast.santa.world.entity.ai.goal.SantaClausCraftGoal;
import net.liukrast.santa.world.entity.ai.goal.SantaClausEatGoal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class SantaClaus extends PathfinderMob implements DeployerGoggleInformation {
    @OnlyIn(Dist.CLIENT)
    public float animationTime;
    @OnlyIn(Dist.CLIENT)
    public State lastFoundState = State.IDLE;

    private static final EntityDataAccessor<Integer> STATE_ID = SynchedEntityData.defineId(SantaClaus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SATISFACTION_A_ID = SynchedEntityData.defineId(SantaClaus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SATISFACTION_B_ID = SynchedEntityData.defineId(SantaClaus.class, EntityDataSerializers.INT);

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
        this.goalSelector.addGoal(0, new SantaClausCraftGoal(this));
        this.goalSelector.addGoal(0, new SantaClausEatGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1) {
            int cooldown = 0;

            @Override
            protected boolean shouldPanic() {
                return getAnimationState() == State.ANGRY;
            }

            @Override
            public void start() {
                super.start();
                cooldown = 0;
            }

            @Override
            public void stop() {
                super.stop();
                setAnimationState(State.IDLE);
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() || cooldown < 60;
            }

            @Override
            public void tick() {
                super.tick();
                cooldown++;
            }
        });
        this.goalSelector.addGoal(1, new SantaClausCollectFoodGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE_ID, 0);
        builder.define(SATISFACTION_A_ID, 0);
        builder.define(SATISFACTION_B_ID, 0);
    }

    /* GETTERS AND SETTERS */

    public State getAnimationState() {
        return State.values()[this.entityData.get(STATE_ID)];
    }

    public void setAnimationState(State value) {
        this.entityData.set(STATE_ID, value.ordinal());
    }

    public boolean isTypeAFood(ItemStack stack) {
        return stack.is(SantaTags.Items.SANTA_FOOD_A);
    }

    public boolean isTypeBFood(ItemStack stack) {
        return stack.is(SantaTags.Items.SANTA_FOOD_B);
    }

    public void incrementSatisfaction(ItemStack stack, boolean typeA) {
        int increment = stack.getCount();
        if(typeA) setSatisfactionA(Mth.clamp(getSatisfactionA()+increment, 0, 100));
        else setSatisfactionB(Mth.clamp(getSatisfactionB()+increment, 0, 100));
    }

    public int getSatisfactionA() {
        return this.entityData.get(SATISFACTION_A_ID);
    }

    public int getSatisfactionB() {
        return this.entityData.get(SATISFACTION_B_ID);
    }

    public void setSatisfactionA(int value) {
        this.entityData.set(SATISFACTION_A_ID, value);
    }

    public void setSatisfactionB(int value) {
        this.entityData.set(SATISFACTION_B_ID, value);
    }

    public boolean isSatisfiedA() {
        return getSatisfactionA() == 100;
    }

    public boolean isSatisfiedB() {
        return getSatisfactionB() == 100;
    }

    /* NBT */
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putIntArray("Satisfaction", new int[]{getSatisfactionA(), getSatisfactionB()});
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        int[] arr = compound.getIntArray("Satisfaction");
        if(arr.length < 2) {
            setSatisfactionA(0);
            setSatisfactionB(0);
        } else {
            setSatisfactionA(arr[0]);
            setSatisfactionB(arr[1]);
        }
    }

    /* LOGIC */

    @Override
    public void tick() {

        if(!isNoAi()) {
            long day = level().getDayTime() % 24000;
            boolean shouldSleep = false;//TODO: day >= SantaConstants.NIGHT_END + SantaConstants.LEAVE_DURATION || day < SantaConstants.NIGHT_START - SantaConstants.LEAVE_DURATION - 6000;
            boolean shouldBeGone = day < SantaConstants.NIGHT_END + SantaConstants.LEAVE_DURATION && day >= SantaConstants.NIGHT_START - SantaConstants.LEAVE_DURATION;

            var state = (getAnimationState() == State.SLEEPING);
            if (shouldBeGone) {
                //TODO: Spawn particles
                this.discard();
            } else if (state != shouldSleep) {
                setAnimationState(shouldSleep ? State.SLEEPING : State.IDLE);
                for (var flag : Goal.Flag.values()) {
                    goalSelector.setControlFlag(flag, shouldSleep);
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        Entity source = damageContainer.getSource().getEntity();
        if(!(source instanceof Player player)) return;
        SantaAttachmentTypes.trust(player, -40);
        if(level().isClientSide) return;
        ((ServerLevel)level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.SNOW_BLOCK.getDefaultInstance()), getX(), getY(), getZ(), 40, 1, 0, 1, 0.4);
        //TODO: Push
    }


    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        SantaLang.translate("gui.santa_claus.info_header").forGoggles(tooltip);
        SantaLang.translate("gui.santa_claus.satisfaction")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        SantaLang.translate("gui.santa_claus.satisfaction_a")
                .add(Component.literal(" " + LangNumberFormat.format(getSatisfactionA())))
                .add(Component.literal("/100 ⭐"))
                .style(IRotate.StressImpact.of(1-getSatisfactionA()/100f).getRelativeColor()).forGoggles(tooltip, 1);
        SantaLang.translate("gui.santa_claus.satisfaction_b")
                .add(Component.literal(" " + LangNumberFormat.format(getSatisfactionB())))
                .add(Component.literal("/100 ⭐"))
                .style(IRotate.StressImpact.of(1-getSatisfactionB()/100f).getRelativeColor()).forGoggles(tooltip, 1);
        return true;
    }

    public enum State {
        IDLE,
        CURIOUS,
        ANGRY,
        EATING,
        SLEEPING,
        CRAFTING
    }

}
