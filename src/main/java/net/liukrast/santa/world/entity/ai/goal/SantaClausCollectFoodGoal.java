package net.liukrast.santa.world.entity.ai.goal;

import net.liukrast.santa.registry.SantaAttachmentTypes;
import net.liukrast.santa.registry.SantaRecipeTypes;
import net.liukrast.santa.world.entity.SantaClaus;
import net.liukrast.santa.world.item.crafting.SantaClausTradingRecipeInput;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class SantaClausCollectFoodGoal extends Goal {
    private final SantaClaus santa;
    private int cooldown;
    public static final int MAX_COOLDOWN = 100;
    private ItemEntity item = null;

    public SantaClausCollectFoodGoal(SantaClaus santa) {
        this.santa = santa;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if(!santa.getMainHandItem().isEmpty()) return false;
        if(item != null && !item.isRemoved()) return false;
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        return !list.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        if(!santa.getMainHandItem().isEmpty()) return false;
        if(item == null) return false;
        if(item.isRemoved()) return false;
        if(!item.isAlive()) return false;
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        return !list.isEmpty();
    }

    public boolean isValidItem(ItemEntity itemEntity) {
        var owner = itemEntity.getOwner();
        return owner != null && owner.isAlive() && owner instanceof Player;
    }

    public int getTrust(ItemEntity itemEntity) {
        var owner = itemEntity.getOwner();
        if(owner == null) return 0;
        if(!owner.isAlive()) return 0;
        return owner.getData(SantaAttachmentTypes.TRUST);
    }

    public AABB getRange(AABB mobBoundingBox) {
        return mobBoundingBox;
    }

    @Override
    public void stop() {
        cooldown = 0;
        if(santa.getAnimationState() == SantaClaus.State.CURIOUS) santa.setAnimationState(SantaClaus.State.IDLE);
    }

    @Override
    public void start() {
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        if(list.isEmpty()) return;
        santa.setAnimationState(SantaClaus.State.CURIOUS);
        item = list.getFirst();
        santa.getLookControl().setLookAt(item);
    }

    @Override
    public void tick() {
        cooldown++;
        if(item != null && !item.isRemoved() && santa.getAnimationState() != SantaClaus.State.ANGRY) santa.getLookControl().setLookAt(item);
        if(cooldown == MAX_COOLDOWN) {
            if(item == null || item.isRemoved()) return;
            List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
            if(!list.contains(item)) return;
            if(item.getOwner() == null) return;
            int trust = getTrust(item);
            var input = new SantaClausTradingRecipeInput(item.getItem(), trust);
            var recipe = santa.level().getRecipeManager().getRecipeFor(SantaRecipeTypes.SANTA_CLAUS_TRADING.get(), input, santa.level());
            if(recipe.isPresent()) {
                var t = item.getOwner().getData(SantaAttachmentTypes.SANTA_TRADE_PROGRESS);
                int count;
                if(t.isPresent()) {
                    int remaining = recipe.get().value().input().count() - t.get().progress();
                    count = Math.min(item.getItem().getCount(), remaining);
                } else {
                    count = Math.min(item.getItem().getCount(), recipe.get().value().input().count());
                }


                santa.setAnimationState(SantaClaus.State.CURIOUS);
                santa.setItemInHand(InteractionHand.MAIN_HAND, item.getItem().copyWithCount(count));
                santa.setLastItemOwner(item.getOwner().getUUID());

                item.setItem(item.getItem().copyWithCount(item.getItem().getCount() - count));
                item.discard();
                return;
            }
            santa.setAnimationState(SantaClaus.State.ANGRY);
        }
    }
}
