package hardcorequesting.client.interfaces.hud;

import hardcorequesting.quests.QuestingData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;

@Environment(EnvType.CLIENT)
public class GUIOverlay extends DrawableHelper {
    
    private MinecraftClient mc;
    
    public GUIOverlay(MinecraftClient mc) {
        this.mc = mc;
    }
    
    /*
    @SubscribeEvent
    public void onRenderExperienceBar(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.getType() != ElementType.EXPERIENCE) {
            return;
        }
        
        int xPos = HQMConfig.OVERLAY_XPOS;
        int yPos = HQMConfig.OVERLAY_YPOS;
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        //String s = " Lives: " + QuestingData.getQuestingData(mc.thePlayer).getLives();
        String s = " Lives: " + getLives();
        String d = " Deaths: " + getDeaths();
        yPos += 10;
        
        if (QuestingData.isHardcoreActive()) {
            if (getLives() <= 2) {
                this.mc.fontRenderer.drawString(s, xPos + 1, yPos, 0xf50505);
            } else {
                this.mc.fontRenderer.drawString(s, xPos + 1, yPos, 0xffffff);
            }
            GlStateManager.disableBlend();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
        } else {
            this.mc.fontRenderer.drawString(d, xPos + 1, yPos, 0xffffff);
        }
    }*/
    
    public int getLives() {
        return QuestingData.getQuestingData(mc.player).getLives();
    }
    
    public int getDeaths() {
        return QuestingData.getQuestingData(mc.player).getDeathStat().getTotalDeaths();
    }
    
}

	