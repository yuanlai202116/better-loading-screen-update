package me.shedaniel.betterloadingscreen.launch;

import me.shedaniel.betterloadingscreen.api.step.LoadGameSteps;
import me.shedaniel.betterloadingscreen.impl.Internals;
import me.shedaniel.betterloadingscreen.impl.JobManagerImpl;

public class BetterLoadingScreenPreInit {
    public static void init(boolean fabric) {
        Internals.manager = new JobManagerImpl();
        LoadGameSteps.loadGame();
        if (!fabric) {
            LoadGameSteps.scanningMods();
            LoadGameSteps.initModsForge();
            LoadGameSteps.registeringContent();
        } else {
            // Register all 3 steps upfront so total stays fixed at 3
            LoadGameSteps.loadGame().stepped(LoadGameSteps.InitMods.COMMON);
            LoadGameSteps.loadGame().stepped(LoadGameSteps.InitMods.CLIENT);
        }
        // Merged: phases 3-5 as one step
        if (fabric) {
            LoadGameSteps.loadAssets();
        } else {
            LoadGameSteps.loadModel();
            LoadGameSteps.prepareModel();
            LoadGameSteps.stitchTexture();
            LoadGameSteps.finalizeRegistry();
            LoadGameSteps.bakeModel();
            LoadGameSteps.finalizeModel();
        }
        Internals.manager.freeze();
    }
}
