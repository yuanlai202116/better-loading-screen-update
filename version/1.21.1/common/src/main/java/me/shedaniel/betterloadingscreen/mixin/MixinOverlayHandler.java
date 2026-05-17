package me.shedaniel.betterloadingscreen.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinOverlayHandler {
    
    @Inject(method = "setOverlay", at = @At("HEAD"))
    private void betterloading$onSetOverlay(Overlay overlay, CallbackInfo ci) {
        if (overlay == null) return;
        
        try {
            Class<?> overlayClass = overlay.getClass();
            java.lang.reflect.Field displayField;
            try {
                displayField = overlayClass.getDeclaredField("displayWindow");
            } catch (NoSuchFieldException e) {
                return;
            }
            
            displayField.setAccessible(true);
            Object displayWindow = displayField.get(overlay);
            if (displayWindow == null) return;
            
            // Close the display window render thread
            try {
                java.lang.reflect.Method closeMethod = displayWindow.getClass().getMethod("close");
                closeMethod.invoke(displayWindow);
            } catch (Exception ignored) {}
            
            // Hide the fmlearlydisplay GLFW window
            try {
                java.lang.reflect.Field winField = displayWindow.getClass().getDeclaredField("window");
                winField.setAccessible(true);
                long handle = winField.getLong(displayWindow);
                if (handle != 0L) {
                    GLFW.glfwHideWindow(handle);
                }
            } catch (Exception ignored) {}
        } catch (Exception ignored) {}
    }
}
