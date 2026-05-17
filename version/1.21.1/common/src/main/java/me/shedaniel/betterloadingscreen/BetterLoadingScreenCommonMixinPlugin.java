package me.shedaniel.betterloadingscreen;

import me.shedaniel.betterloadingscreen.launch.BetterLoadingScreenPreInit;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class BetterLoadingScreenCommonMixinPlugin implements IMixinConfigPlugin {
    private static Boolean hasOptifine;
    
    public static boolean hasOptifine() {
        if (hasOptifine == null) {
            hasOptifine = _hasOptifine();
        }
        return hasOptifine;
    }
    
    private static boolean _hasOptifine() {
        try {
            Class.forName("optifine.OptiFineTransformationService");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    @Override
    public void onLoad(String mixinPackage) {
        // For NeoForge, initialize here if not already initialized by platform-specific code
        if (!isInitialized()) {
            try {
                // Detect NeoForge by checking if its FMLLoader class exists
                Class.forName("net.neoforged.fml.loading.FMLLoader");
                BetterLoadingScreenPreInit.init(false);
            } catch (ClassNotFoundException e) {
                // Not NeoForge
            }
        }
    }
    
    private static boolean isInitialized() {
        try {
            return me.shedaniel.betterloadingscreen.impl.Internals.manager != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }
    
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }
    
    @Override
    public List<String> getMixins() {
        return null;
    }
    
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
    
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
