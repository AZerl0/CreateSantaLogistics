package net.liukrast.santa.world.entity.ai.goal;

import net.liukrast.santa.world.entity.SantaClaus;
import net.liukrast.santa.world.level.levelgen.SantaBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;

public class SantaGoToBedGoal extends MoveToBlockGoal {
    private final SantaClaus santa;
    public SantaGoToBedGoal(SantaClaus mob, double speedModifier) {
        super(mob, speedModifier, 0);
        this.santa = mob;
    }

    @Override
    public boolean canUse() {
        return santa.shouldGoToSleep() && findNearestBlock();
    }

    @Override
    protected boolean findNearestBlock() {
        BlockPos pos = SantaBase.getCouch((ServerLevel) this.mob.level());
        if(pos == null) return false;
        this.blockPos = pos.offset(-1, 0, -1);
        return true;
    }

    @Override
    protected void moveMobToBlock() {
        this.mob.getNavigation()
                .moveTo(
                        (double)this.blockPos.getX() + 0.5,
                        this.blockPos.getY() + 1,
                        (double)this.blockPos.getZ() + 0.5,
                        0,
                        this.speedModifier);
    }

    @Override
    protected boolean isValidTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos) {
        return false;
    }
}
