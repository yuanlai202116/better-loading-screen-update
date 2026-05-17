package me.shedaniel.betterloadingscreen;

import me.shedaniel.betterloadingscreen.api.render.AbstractGraphics;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.function.Supplier;

public enum MinecraftGraphics implements AbstractGraphics {
    INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger(MinecraftGraphics.class);
    public static Font vanillaFont;
    private static PoseStack currentPoseStack;
    
    public static void setCurrentPoseStack(PoseStack poseStack) { currentPoseStack = poseStack; }
    public static Font getFont() { if (vanillaFont != null) return vanillaFont; return Minecraft.getInstance().font; }
    private static PoseStack getPoseStack() { return currentPoseStack; }

    @Override
    public void fill(int x1, int y1, int x2, int y2, int color) {
        PoseStack ps = getPoseStack();
        if (ps != null) GuiComponent.fill(ps, x1, y1, x2, y2, color);
    }

    @Override
    public void bindTexture(String textureId) {}
    @Override
    public boolean bindTextureCustomStream(String textureId, Supplier<InputStream> supplier) { return false; }

    @Override
    public void drawString(String string, int x, int y, int color) { PoseStack ps = getPoseStack(); Font f = getFont(); if (ps != null && f != null) f.draw(ps, string, x, y, color); }

    @Override
    public void drawStringWithShadow(String string, int x, int y, int color) { drawString(string, x, y, color); }

    @Override
    public int width(String string) { Font f = getFont(); return f != null ? f.width(string) : string.length() * 6; }

    @Override
    public int getScaledWidth() { return Minecraft.getInstance().getWindow().getGuiScaledWidth(); }
    @Override
    public int getScaledHeight() { return Minecraft.getInstance().getWindow().getGuiScaledHeight(); }
    @Override
    public void innerBlit(int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2, int color) {}
}




