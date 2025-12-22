package net.liukrast.santa.client.gui.screens;

import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.widget.IconButton;
import net.liukrast.santa.SantaConstants;
import net.liukrast.santa.network.protocol.game.RoboElfCraftPacket;
import net.liukrast.santa.registry.SantaAttachmentTypes;
import net.liukrast.santa.world.entity.RoboElf;
import net.liukrast.santa.world.entity.TradeInfo;
import net.liukrast.santa.world.inventory.RoboElfMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.neoforged.neoforge.network.PacketDistributor;

public class RoboElfScreen extends AbstractSimiContainerScreen<RoboElfMenu> {
    private static final ResourceLocation TEXTURE = SantaConstants.id("textures/gui/robo_elf.png");

    private static final Component ENQUEUE = Component.translatable("container.santa_logistics.robo_elf.enqueue");
    private static final Component QUEUE_FULL = Component.translatable("container.santa_logistics.robo_elf.queue_full");
    private static final Component NE_ITEMS = Component.translatable("container.santa_logistics.robo_elf.not_enough_items");
    private static final Component N_SELECTED = Component.translatable("container.santa_logistics.robo_elf.no_recipe_selected");

    private static final Component SCROLL_TO_CHANGE = Component.translatable("container.santa_logistics.robo_elf.scroll_to_change_amount");

    private static final Component TRUST_LEVEL = Component.translatable("container.santa_logistics.robo_elf.trust_level");
    private static final Component TRUST_GAIN = Component.translatable("container.santa_logistics.robo_elf.trust_gain");
    private static final Component ENERGY_USAGE = Component.translatable("container.santa_logistics.robo_elf.energy_usage");
    private static final Component PROCESS_TIME = Component.translatable("container.santa_logistics.robo_elf.process_time");

    private int selected = -1;
    private int scroll = 0;
    private int lastTrust = 0;
    private final int questsPerPage;
    int queueAmount = 1;
    private IconButton confirmButton;
    public RoboElfScreen(RoboElfMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        questsPerPage = 3;
        imageHeight = questsPerPage*24+42+115;
        imageWidth = 224;
    }

    @Override
    protected void init() {
        super.init();
        confirmButton = new IconButton(leftPos+imageWidth-68, topPos+19, AllIcons.I_3x3) {
            @Override
            public void setToolTip(Component text) {
                if(text == null) this.toolTip.clear();
                else super.setToolTip(text);
            }
        };
        confirmButton.withCallback(() -> {
            if(selected < 0 || selected >= menu.trades.size()) return;
            PacketDistributor.sendToServer(new RoboElfCraftPacket(menu.entity.getId(), selected, queueAmount));
        });
        reloadButton();
        addRenderableWidget(confirmButton);
    }

    private void reloadButton() {
        if(confirmButton == null) return;
        State state = validation();
        confirmButton.setActive(state == State.VALID);
        confirmButton.setToolTip(switch (state) {
            case VALID -> ENQUEUE;
            case QUEUE_FULL -> QUEUE_FULL;
            case NE_ITEMS -> NE_ITEMS;
            case N_SELECT -> Component.empty();
        });
    }

    private State validation() {
        if(selected < 0 || selected >= menu.trades.size()) return State.N_SELECT;
        assert Minecraft.getInstance().player != null;
        var inventory = Minecraft.getInstance().player.getInventory();
        for (ItemCost cost : menu.trades.get(selected).getIngredients()) {
            if (cost == null) continue;
            int needed = cost.count();
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (ItemStack.isSameItemSameComponents(stack, cost.itemStack())) {
                    needed -= stack.getCount();
                }
                if (needed <= 0) break;
            }
            if (needed > 0) {
                return State.NE_ITEMS;
            }
        }
        if(menu.entity.getQueueSize()+menu.entity.getCraftedSize()+queueAmount >= RoboElf.MAX_QUEUE) return State.QUEUE_FULL;
        return State.VALID;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        var mc = Minecraft.getInstance();
        assert mc.player != null;
        int real = mc.player.getData(SantaAttachmentTypes.TRUST);
        int re = Math.round((lastTrust+real)/2f);
        lastTrust = Math.abs(real-re) <= 1 ? real : re;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        guiGraphics.blit(TEXTURE, guiLeft, guiTop, 0, 0, 224, 42);

        /* TRUST */
        String trust = lastTrust + "☺";
        int tw = font.width(trust);
        int st = guiLeft+imageWidth-24-tw;
        guiGraphics.blit(TEXTURE, st, guiTop+20, 224, 38, 8, 16);
        for(int i = 0; i < tw; i++) {
            guiGraphics.blit(TEXTURE, st+8+i, guiTop+ 20, 232, 38, 1, 16);
        }
        guiGraphics.blit(TEXTURE, st+8+tw, guiTop+20, 233, 38, 8, 16);
        renderTextWithTooltip(guiGraphics, trust, st+8, guiTop+24, 0xb59370, TRUST_LEVEL, mouseX, mouseY);

        /* TRADES */
        for(int i = 0; i < questsPerPage; i++) {
            guiGraphics.blit(TEXTURE, guiLeft, guiTop + 42 + 24*i, 0, 42, 224, 24);
            if(i+scroll >= menu.trades.size()) continue;
            TradeInfo info = menu.trades.get(i+scroll);
            var ing = info.getIngredients();

            for(int k = 0; k < ing.length; k++) {
                ItemCost c = ing[k];
                if(c == null || c.itemStack().isEmpty()) continue;
                renderItem(guiGraphics, c.itemStack(),guiLeft + 25 + k*20, guiTop + 45 + 24*i, mouseX, mouseY);
            }

            renderTextWithTooltip(
                    guiGraphics,
                    "+" + info.getTrustGain() + "☺",
                    guiLeft + 85, guiTop + 51 + 24*i,
                    0x603d39, TRUST_GAIN,
                    mouseX, mouseY
            );

            renderTextWithTooltip(
                    guiGraphics,
                    "-" + info.getEnergy() + "⚡",
                    guiLeft + 85+30, guiTop + 51 + 24*i,
                    0x603d39, ENERGY_USAGE,
                    mouseX, mouseY
            );

            renderTextWithTooltip(
                    guiGraphics,
                    info.getProcessTime() + "⌚",
                    guiLeft + 85+60, guiTop + 51 + 24*i,
                    0x603d39, PROCESS_TIME,
                    mouseX, mouseY
            );
            renderItem(guiGraphics, info.getResult(), guiLeft + 181, guiTop + 45 + 24*i, mouseX, mouseY);
            if(selected == i+scroll || inBox(mouseX, mouseY, guiLeft, guiTop + 42 + 24*i, 224, 24))
                guiGraphics.blit(TEXTURE, guiLeft+23, guiTop + 44 + 24*i, 23, 181, 178, 20);
        }
        guiGraphics.blit(TEXTURE, guiLeft, guiTop+42+24*questsPerPage, 0, 66, 224, 115);

        if(scroll<menu.trades.size()-3) {
            guiGraphics.blit(TEXTURE, guiLeft+(imageWidth>>1)-12, guiTop+38+questsPerPage*24, 229, 0, 24, 10);
        }
        if(selected>=0&&selected<menu.trades.size()) {
            ItemStack result = menu.trades.get(selected).getResult();
            guiGraphics.drawString(font, Component
                            .literal(queueAmount + "x ")
                            .append(result.getHoverName()).withStyle(style -> style.withColor(0xb59370)),
                    guiLeft + 24, guiTop + 24, 0xb59370, false);
        }

        if(scroll > 0) {
            guiGraphics.blit(TEXTURE, guiLeft+(imageWidth>>1)-12, guiTop+16, 229, 9, 24, 10);
        }
        if(inBox(mouseX, mouseY,guiLeft+21, guiTop+19, 109, 18)) {
            guiGraphics.renderTooltip(font, SCROLL_TO_CHANGE, mouseX, mouseY);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int pButton) {
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        for(int i = 0; i < questsPerPage; i++) {
            if(i+scroll >= menu.trades.size()) continue;
            if(inBox((int) mouseX, (int) mouseY, guiLeft, guiTop + 42 + 24*i, 224, 24)) {
                selected = i+scroll;
                reloadButton();
                return true;
            }
        }
        super.mouseClicked(mouseX, mouseY, pButton);
        selected = -1;
        reloadButton();
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int guiLeft = getGuiLeft();
        int guiTop = getGuiTop();
        if(inBox((int) mouseX, (int) mouseY,guiLeft+21, guiTop+19, 109, 18)) {
            queueAmount = (int) Mth.clamp(queueAmount+scrollY, 1, 9);
            reloadButton();
            return true;
        }
        scroll = Math.clamp((int)(scroll-scrollY), 0, menu.trades.size()-3);
        return true;
    }

    private boolean inBox(int x, int y, int u, int v, int w, int h) {
        return x >= u && x < u+w && y >= v && y < v+h;
    }

    private void renderTextWithTooltip(GuiGraphics guiGraphics, String text, int x, int y, int color, Component tooltip, int mouseX, int mouseY) {
        int w = font.width(text);
        guiGraphics.drawString(font, text, x, y, color, false);
        if(inBox(mouseX, mouseY, x, y, w, font.lineHeight))
            guiGraphics.renderTooltip(font, tooltip, mouseX, mouseY);
    }

    private void renderItem(GuiGraphics graphics, ItemStack stack, int x, int y, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, x, y, 224, 20, 18, 18);
        graphics.renderItem(stack, x+1, y+1);
        graphics.renderItemDecorations(font, stack, x+1, y+1);
        if(inBox(mouseX, mouseY, x, y, 18, 18))
            graphics.renderTooltip(font, stack, mouseX, mouseY);
    }

    private enum State {
        VALID, QUEUE_FULL, NE_ITEMS, N_SELECT
    }
}
