package me.shedaniel.betterloadingscreen.mixin;

import me.shedaniel.betterloadingscreen.MinecraftGraphics;
import net.minecraft.client.gui.font.FontManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FontManager.class)
public class MixinFont {
    private void init() {
        MinecraftGraphics.vanillaFont = null;
    }
}

