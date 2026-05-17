package me.shedaniel.betterloadingscreen.mixin;

import me.shedaniel.betterloadingscreen.api.step.LoadGameSteps;
import me.shedaniel.betterloadingscreen.api.step.SteppedTask;
import me.shedaniel.betterloadingscreen.impl.mixinstub.MinecraftStub;
import me.shedaniel.betterloadingscreen.impl.mixinstub.ModelBakeryStub;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ModelBakery.class)
public class MixinModelBakery implements ModelBakeryStub {
    @Unique private SteppedTask blockTask;
    @Unique private SteppedTask itemTask;
    
    @Override
    public void betterloadingscreen$setBlockTask(SteppedTask task) {
        this.blockTask = task;
    }
    
    @Override
    public SteppedTask betterloadingscreen$getBlockTask() {
        return blockTask;
    }
    
    @Override
    public void betterloadingscreen$setItemTask(SteppedTask task) {
        this.itemTask = task;
    }
    
    @Override
    public SteppedTask betterloadingscreen$getItemTask() {
        return itemTask;
    }
    
    @Inject(method = {"method_4716", "lambda$processLoading$8"}, at = @At("HEAD"), require = 0)
    private void startBlock(BlockState blockState, CallbackInfo ci) {
        if (blockTask != null) blockTask.setCurrentStepInfo(BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString());
    }
    
    @Inject(method = {"method_4716", "lambda$processLoading$8"}, at = @At("RETURN"), require = 0)
    private void endBlock(BlockState blockState, CallbackInfo ci) {
        if (blockTask != null) blockTask.incrementStep();
    }
    
    @Inject(method = "uploadTextures", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;keySet()Ljava/util/Set;",
            ordinal = 0
    ), require = 0)
    private void preBaking(TextureManager textureManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<Object> cir) {
        ((MinecraftStub) Minecraft.getInstance()).moveRenderOut();
    }
    
    @Inject(method = {"method_4733", "lambda$uploadTextures$12", "m_119368_"}, at = @At("HEAD"), require = 0)
    private void startBaking(Identifier Identifier, CallbackInfo ci) {
        LoadGameSteps.bakeModel().setCurrentStepInfo(Identifier.toString());
    }
    
    @Inject(method = {"method_4733", "lambda$uploadTextures$12", "m_119368_"}, at = @At("RETURN"), require = 0)
    private void endBaking(Identifier Identifier, CallbackInfo ci) {
        LoadGameSteps.bakeModel().incrementStep();
    }
    
    @Inject(method = "uploadTextures", at = @At(
            value = "RETURN"
    ), require = 0)
    private void postBaking(TextureManager textureManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<Object> cir) {
        ((MinecraftStub) Minecraft.getInstance()).moveRenderIn();
    }
}
