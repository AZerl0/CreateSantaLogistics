package net.liukrast.santa.world.level.block.entity.behaviour;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class OverclockScrollValueBehaviour extends ScrollValueBehaviour {
    public OverclockScrollValueBehaviour(Component label, SmartBlockEntity be, ValueBoxTransform slot) {
        super(label, be, slot);
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        ImmutableList<Component> rows = ImmutableList.of(
                Component.literal("①").withStyle(ChatFormatting.BOLD),
                Component.literal("②").withStyle(ChatFormatting.BOLD),
                Component.literal("③").withStyle(ChatFormatting.BOLD),
                Component.literal("④").withStyle(ChatFormatting.BOLD)
        );
        ValueSettingsFormatter formatter = new ValueSettingsFormatter(this::formatSettings);
        return new ValueSettingsBoard(label, 256, 32, rows, formatter);
    }

    @Override
    public void setValueSettings(Player player, ValueSettings valueSetting, boolean ctrlHeld) {
        int value = valueSetting.value();
        if (!valueSetting.equals(getValueSettings()))
            playFeedbackSound(this);
        setValue((valueSetting.row())*256+value);
    }

    @Override
    public ValueSettings getValueSettings() {
        return new ValueSettings(value/256, value%256);
    }

    public MutableComponent formatSettings(ValueSettings settings) {
        return CreateLang.number(settings.row()*256+settings.value())
                .component();
    }

    @Override
    public String getClipboardKey() {
        return "Overclock";
    }
}
