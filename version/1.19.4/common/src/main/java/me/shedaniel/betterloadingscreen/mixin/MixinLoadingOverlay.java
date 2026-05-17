package me.shedaniel.betterloadingscreen.mixin;

import com.mojang.blaze3d.vertex.PoseStack;

import me.shedaniel.betterloadingscreen.BetterLoadingScreen;
import me.shedaniel.betterloadingscreen.BetterLoadingScreenClient;
import me.shedaniel.betterloadingscreen.MinecraftGraphics;
import me.shedaniel.betterloadingscreen.api.step.LoadGameSteps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LoadingOverlay.class)
public abstract class MixinLoadingOverlay {
    @Shadow private long fadeOutStart;
    
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private ReloadInstance reload;
    
    @Unique private static boolean progressInited = false;
    
    @Inject(method = "render", at = @At("RETURN"))
    private void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        MinecraftGraphics.setCurrentPoseStack(poseStack);
        
        float realProgress = this.reload.getActualProgress();
        if (!progressInited && realProgress > 0.0f) {
            progressInited = true;
            me.shedaniel.betterloadingscreen.api.step.SteppedTask loadLoad = me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.LOAD);
            me.shedaniel.betterloadingscreen.api.step.SteppedTask extractStep = me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.EXTRACT);
            me.shedaniel.betterloadingscreen.api.step.SteppedTask stitchStep = me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.STITCH);
            me.shedaniel.betterloadingscreen.api.step.SteppedTask bakeStep = me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.BAKE);
            loadLoad.setTotalSteps(100); loadLoad.setCurrentStep(1);
            extractStep.setTotalSteps(100); extractStep.setCurrentStep(1);
            stitchStep.setTotalSteps(100); stitchStep.setCurrentStep(1);
            bakeStep.setTotalSteps(100); bakeStep.setCurrentStep(1);
        }
        
        if (progressInited) {
            int p = (int)(realProgress * 100);
            me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.LOAD).setCurrentStep(p);
            me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.EXTRACT).setCurrentStep(p);
            me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.STITCH).setCurrentStep(p);
            me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.loadAssets().stepped(me.shedaniel.betterloadingscreen.api.step.LoadGameSteps.LoadAssets.BAKE).setCurrentStep(p);
        }
        
        float g = this.fadeOutStart > -1L ? (float) (System.currentTimeMillis() - this.fadeOutStart) / 1000.0F : -1.0F;
        if (g < 1.0F) {
            BetterLoadingScreenClient.renderOverlay(MinecraftGraphics.INSTANCE, i, j, f, 1.0F - Mth.clamp(g, 0.0F, 1.0F));
        }
    }
    
    @Redirect(method = "render", require = 0, at = @At(value = "INVOKE",
                                          target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;drawProgressBar(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIF)V"))
    private void drawProgressBar(LoadingOverlay instance, PoseStack poseStack, int i, int j, int k, int l, float f) {
    }
    
    @Inject(method = "getBrandColor", at = @At(value = "HEAD"), cancellable = true, require = 0)
    private static void getBrandColor(CallbackInfoReturnable<Integer> cir) {
        int bgColor = BetterLoadingScreenClient.renderer.getBackgroundColor() | 0xFF000000;
        cir.setReturnValue(bgColor);
    }
    
    @Unique
    private static final ResourceLocation BACKGROUND_PATH = new ResourceLocation(BetterLoadingScreen.MOD_ID, "background.png");
    @Unique
    private static Boolean hasCustomBackground;
    
    @Inject(method = "render", at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/platform/Window;getGuiScaledWidth()I",
            ordinal = 1
    ), require = 0)
    private void renderBackground(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
    }
}
