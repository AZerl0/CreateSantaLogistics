package net.liukrast.santa.client.gui.screens;

import com.simibubi.create.AllKeys;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsPacket;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class FrostburnEngineScreen extends AbstractSimiScreen {

    private final ScrollValueBehaviour behaviour;
    private int overclock;
    public FrostburnEngineScreen(ScrollValueBehaviour behaviour) {
        super(Component.empty());
        this.behaviour = behaviour;
        overclock = behaviour.value;
    }

    @Override
    protected void init() {
        super.init();
        var button = new IconButton(guiLeft, guiTop, AllIcons.I_CONFIG_BACK);
        button.withCallback(this::sendIt);
        addRenderableWidget(button);
    }

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.drawString(this.font, "" + overclock, guiLeft + 200, guiTop, 0xFFFFFF);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {

        overclock = (int) Mth.clamp(overclock + (scrollY * (AllKeys.ctrlDown() ? 10 : 1)), 0, 1024);
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private void sendIt() {
        CatnipServices.NETWORK
                .sendToServer(new ValueSettingsPacket(
                        behaviour.blockEntity.getBlockPos(),
                        0, overclock,
                        null, null, Direction.UP,
                        AllKeys.ctrlDown(), behaviour.netId()
                ));
        assert minecraft != null;
        minecraft.setScreen(null);
    }
}
