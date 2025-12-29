package net.liukrast.santa.world.entity.ai.goal;

import net.createmod.catnip.math.VecHelper;
import net.liukrast.santa.registry.SantaAttachmentTypes;
import net.liukrast.santa.registry.SantaRecipeTypes;
import net.liukrast.santa.world.entity.SantaClaus;
import net.liukrast.santa.world.entity.attachment.SantaTradeProgress;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipeInput;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Optional;

public class SantaClausEatGoal extends Goal {
    private int cooldown;
    private final SantaClaus santa;
    private boolean canContinueUse;
    private boolean isFood;

    public SantaClausEatGoal(SantaClaus santa) {
        this.santa = santa;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        var stack = santa.getMainHandItem();
        var owner = santa.getLastItemOwner();
        if(owner == null) return false;
        Player player = santa.level().getPlayerByUUID(owner);
        if(player == null) return false;
        return santa.level().getRecipeManager().getRecipeFor(SantaRecipeTypes.SANTA_CLAUS_TRADING.get(), new SantaClausTradingRecipeInput(
                stack, player.getData(SantaAttachmentTypes.TRUST)
        ), santa.level()).isPresent();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && canContinueUse;
    }

    @Override
    public void start() {
        var stack = santa.getMainHandItem();
        var owner = santa.getLastItemOwner();
        if(owner != null) {
            Player player = santa.level().getPlayerByUUID(owner);
            if(player != null && santa.level().getRecipeManager().getRecipeFor(SantaRecipeTypes.SANTA_CLAUS_TRADING.get(), new SantaClausTradingRecipeInput(stack, player.getData(SantaAttachmentTypes.TRUST)), santa.level()).map(rh -> rh.value().food()).orElse(false)) {
                isFood = true;
            }
        }
        santa.setAnimationState(isFood ? SantaClaus.State.EATING : SantaClaus.State.LOOTING);
        cooldown = 20;
        canContinueUse = true;
    }

    @Override
    public void stop() {
        santa.setAnimationState(SantaClaus.State.IDLE);
        isFood = false;
    }

    @Override
    public void tick() {
        if(cooldown > 0) {
            cooldown--;
            if(!isFood) return;
            Vec3 m = VecHelper.offsetRandomly(new Vec3(0, 0.25f, 0), santa.getRandom(), .125f);
            ((ServerLevel)santa.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, santa.getMainHandItem()), santa.getX(), santa.getY() + 2, santa.getZ(), 10, m.x, m.y, m.z, 0.1);
        } else {
            var stack = santa.getMainHandItem();
            var owner = santa.getLastItemOwner();
            if(owner == null) return;
            Player player = santa.level().getPlayerByUUID(owner);
            if(player == null) return;
            var recipe = santa.level().getRecipeManager().getRecipeFor(SantaRecipeTypes.SANTA_CLAUS_TRADING.get(), new SantaClausTradingRecipeInput(
                    stack, player.getData(SantaAttachmentTypes.TRUST)
            ), santa.level());
            var extraData = player.getData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS);
            if(recipe.isEmpty()) {
                if(extraData.isPresent()) {
                    player.setData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS, Optional.empty());
                }
                return;
            }
            if(extraData.isPresent()) {
                var ed = extraData.get();
                if(ItemStack.isSameItemSameComponents(ed.recipe().result(), recipe.get().value().result())) {
                    player.setData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS, Optional.of(ed.increment()));
                } else {
                    player.setData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS, Optional.of(new SantaTradeProgress(1, recipe.get().value())));
                }
            } else player.setData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS, Optional.of(new SantaTradeProgress(1, recipe.get().value())));
            santa.getMainHandItem().consume(1, santa);
            if(santa.getMainHandItem().isEmpty() && extraData.isPresent() && extraData.get().progress() >= recipe.get().value().input().count()) {
                player.setData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS, Optional.of(new SantaTradeProgress(extraData.get().progress() - recipe.get().value().input().count(), recipe.get().value())));
                santa.setItemInHand(InteractionHand.MAIN_HAND, recipe.get().value().result());
            }
            canContinueUse = false;
        }
    }
}
