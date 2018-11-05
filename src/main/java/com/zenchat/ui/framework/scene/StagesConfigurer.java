package com.zenchat.ui.framework.scene;

import com.zenchat.ui.framework.component.ComponentProvider;
import com.zenchat.ui.framework.component.ComponentRegistry;
import com.zenchat.ui.framework.module.ModuleProvider;

public class StagesConfigurer {


    public static void configureStages(ModuleProvider... moduleProviders) {
        Scenes scenes = new Scenes();

        for (ModuleProvider moduleProvider : moduleProviders) {
            ComponentRegistry componentRegistry = moduleProvider.getComponentRegistry();

            for (ComponentProvider componentProvider : componentRegistry.getComponentProviders()) {
                componentProvider.getComponent().show(moduleProvider.getStage());

                scenes.registerScene(
                        componentProvider.getComponentName(),
                        componentProvider.getComponent().fxView(),
                        moduleProvider.getStage()
                );
            }
        }

        Scenes.constructSingleton(scenes);
    }

}
