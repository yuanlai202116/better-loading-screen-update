package me.shedaniel.betterloadingscreen.mixin.fabric;

import me.shedaniel.betterloadingscreen.api.step.LoadGameSteps;
import me.shedaniel.betterloadingscreen.api.step.ParentTask;
import me.shedaniel.betterloadingscreen.api.step.SteppedTask;
import me.shedaniel.betterloadingscreen.impl.mixinstub.ModelBakeryStub;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(ModelBakery.class)
public class MixinModelBakeryFabric {
    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V",
            ordinal = 0
    ), require = 0)
    private void init(ResourceManager resourceManager, BlockColors blockColors, ProfilerFiller profilerFiller, int i, CallbackInfo ci) {
        int stateCount = 0, itemCount = BuiltInRegistries.ITEM.size();
        for (Block block : BuiltInRegistries.BLOCK) {
            stateCount += block.getStateDefinition().getPossibleStates().size();
        }
        ParentTask task = LoadGameSteps.loadModel();
        SteppedTask blockTask = task.stepped(LoadGameSteps.LoadModel.BLOCK);
        blockTask.setTotalSteps(stateCount);
        SteppedTask itemTask = task.stepped(LoadGameSteps.LoadModel.ITEM);
        itemTask.setTotalSteps(itemCount);
        ((ModelBakeryStub) this).betterloadingscreen$setBlockTask(blockTask);
        ((ModelBakeryStub) this).betterloadingscreen$setItemTask(itemTask);
    }
    
    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBakery;loadTopLevel(Lnet/minecraft/client/resources/model/Identifier;)V",
            ordinal = 1
    ), locals = LocalCapture.CAPTURE_FAILHARD, require = 0)
    private void startItem(ResourceManager resourceManager, BlockColors blockColors, ProfilerFiller profilerFiller, int i, CallbackInfo ci,
            Iterator iterator, Identifier Identifier) {
        ((ModelBakeryStub) this).betterloadingscreen$getItemTask().setCurrentStepInfo(Identifier.toString());
    }
    
    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBakery;loadTopLevel(Lnet/minecraft/client/resources/model/Identifier;)V",
            ordinal = 1,
            shift = At.Shift.AFTER
    ), require = 0)
    private void endItem(ResourceManager resourceManager, BlockColors blockColors, ProfilerFiller profilerFiller, int i, CallbackInfo ci) {
        ((ModelBakeryStub) this).betterloadingscreen$getItemTask().incrementStep();
    }
}
