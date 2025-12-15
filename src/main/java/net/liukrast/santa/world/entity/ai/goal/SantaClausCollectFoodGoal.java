package net.liukrast.santa.world.entity.ai.goal;

import com.simibubi.create.AllFluids;
import net.liukrast.santa.registry.SantaAttachmentTypes;
import net.liukrast.santa.world.entity.SantaClaus;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class SantaClausCollectFoodGoal extends Goal {
    private final SantaClaus santa;
    private int cooldown;
    private static final int MAX_COOLDOWN = 100;
    private ItemEntity item = null;

    public SantaClausCollectFoodGoal(SantaClaus santa) {
        this.santa = santa;
    setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if(!santa.getMainHandItem().isEmpty()) return false;
        if(item != null) return false;
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        return !list.isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        if(!santa.getMainHandItem().isEmpty()) return false;
        if(item == null) return false;
        if(item.isRemoved()) return false;
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        return !list.isEmpty();
    }

    public boolean isValidItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        var owner = itemEntity.getOwner();
        if(owner == null) return false;
        if(owner.isAlive()) return false;
        if(owner.getData(SantaAttachmentTypes.TRUST) < 1000) return false;
        return stack.is(Items.MILK_BUCKET) || stack.is(Items.COOKIE) || stack.is(AllFluids.CHOCOLATE.getBucket().orElseThrow());
    }

    public AABB getRange(AABB mobBoundingBox) {
        return mobBoundingBox;
    }

    @Override
    public void stop() {
        cooldown = 0;
    }

    @Override
    public void start() {
        List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
        if(list.isEmpty()) return;
        item = list.getFirst();
        santa.getLookControl().setLookAt(item);
    }

    @Override
    public void tick() {
        cooldown++;
        if(cooldown == MAX_COOLDOWN) {
            if(item == null || item.isRemoved()) return;
            List<ItemEntity> list = santa.level().getEntitiesOfClass(ItemEntity.class, getRange(santa.getBoundingBox()), this::isValidItem);
            if(!list.contains(item)) return;
            santa.setItemInHand(InteractionHand.MAIN_HAND, item.getItem());
            item.discard();
        }
    }
}
