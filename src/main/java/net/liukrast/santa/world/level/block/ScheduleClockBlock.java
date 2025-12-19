package net.liukrast.santa.world.level.block;

import net.liukrast.multipart.block.AbstractFacingMultipartBlock;
import net.liukrast.santa.DeployerGoggleInformation;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.SantaLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ScheduleClockBlock extends AbstractFacingMultipartBlock implements DeployerGoggleInformation {
    public ScheduleClockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void defineParts(Builder builder) {
        builder.define(0,0,0);
        builder.define(0,1,0);
        builder.define(0,2,0);
    }

    @Override
    public boolean addToGoogleTooltip(Level level, BlockPos pos, BlockState state, List<Component> tooltip, boolean isPlayerSneaking) {
        SantaLang.translate("gui.schedule_clock.info_header")
                .forGoggles(tooltip);
        SantaLang.translate("gui.schedule_clock.current_time", formatTime(level.getDayTime()))
                .style(ChatFormatting.GOLD)
                .forGoggles(tooltip);
        SantaLang.translate("gui.schedule_clock.schedule")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        SantaLang.translate("gui.schedule_clock.wake_up_time", Component.literal(formatTime(SantaConstants.NIGHT_START-SantaConstants.LEAVE_DURATION-6000))
                        .withStyle(ChatFormatting.AQUA)
                ).style(ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip,1);
        SantaLang.translate("gui.schedule_clock.leave_time", Component.literal(formatTime(SantaConstants.NIGHT_START-SantaConstants.LEAVE_DURATION))
                        .withStyle(ChatFormatting.AQUA)
                ).style(ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip, 1);
        SantaLang.translate("gui.schedule_clock.get_back_time", Component.literal(formatTime(SantaConstants.NIGHT_END+SantaConstants.LEAVE_DURATION))
                        .withStyle(ChatFormatting.AQUA)
                ).style(ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip, 1);

        return true;
    }

    private String formatTime(long time) {
        time = time%24000;
        int totalMinutes = (int) ((time + 6000) % 24000 * 1440 / 24000);

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}
