package com.zenchat.ui.framework.scene;

import com.zenchat.ui.framework.component.ComponentProvider;
import com.zenchat.ui.framework.component.ComponentRegistry;
import com.zenchat.ui.framework.module.ModuleProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StagesConfigurer {

    public static void configureStages(ModuleProvider... moduleProviders) {
        Scenes scenes = new Scenes();

        for (ModuleProvider moduleProvider : moduleProviders) {
            log.info("Loading module: '{}'", moduleProvider.getModuleName());

            ComponentRegistry componentRegistry = moduleProvider.getComponentRegistry();

            for (ComponentProvider componentProvider : componentRegistry.getComponentProviders()) {
                log.info("Loading component '{}'", componentProvider.getComponentName());

                componentProvider.getComponent().onInit();

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
