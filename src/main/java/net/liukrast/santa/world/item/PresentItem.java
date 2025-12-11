package net.liukrast.santa.world.item;

import com.simibubi.create.AllDamageTypes;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.item.ItemHelper;
import net.liukrast.santa.registry.SantaDataComponentTypes;
import net.liukrast.santa.registry.SantaItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredItem;
import org.lwjgl.system.NonnullDefault;

import java.util.List;
import java.util.Random;

@NonnullDefault
public class PresentItem extends PackageItem {
    private static final Random STYLE_PICKER = new Random();
    public PresentItem(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);
        (style.rare() ? PackageStyles.RARE_BOXES : PackageStyles.STANDARD_BOXES).remove(this);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    @Override
    public boolean canBeHurtBy(ItemStack stack, DamageSource source) {
        if(source.is(AllDamageTypes.SAW)) return false;
        if(source.getEntity() instanceof Player player) {
            String address = getAddress(stack);
            if(!player.getAbilities().instabuild && (!address.isEmpty() && !player.getScoreboardName().equals(address))) return false;
        }
        return super.canBeHurtBy(stack, source);
    }

    public static ItemStack containing(List<ItemStack> stacks) {
        ItemStackHandler newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return containing(newInv);
    }

    public static ItemStack containing(ItemStackHandler stacks) {
        List<DeferredItem<PresentItem>> pool = SantaItems.PRESENTS;
        ItemStack box = pool.get(STYLE_PICKER.nextInt(pool.size())).toStack();
        box.set(SantaDataComponentTypes.HIDDEN_CONTENTS.get(), ItemHelper.containerContentsFromHandler(stacks));
        return box;
    }

    public static ItemStackHandler getContents(ItemStack box) {
        ItemStackHandler newInv = new ItemStackHandler(9);
        ItemContainerContents contents = box.getOrDefault(SantaDataComponentTypes.HIDDEN_CONTENTS, ItemContainerContents.EMPTY);
        ItemHelper.fillItemStackHandler(contents, newInv);
        return newInv;
    }

    @Override
    public InteractionResultHolder<ItemStack> open(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack box = playerIn.getItemInHand(handIn);
        String address = getAddress(box);
        if(address.isEmpty()) return super.open(worldIn, playerIn, handIn);
        if(playerIn.getScoreboardName().equals(address)) return super.open(worldIn, playerIn, handIn);
        return new InteractionResultHolder<>(InteractionResult.FAIL, box);
    }
}
