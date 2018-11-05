package com.zenchat.ui.framework.scene;

import com.zenchat.ui.framework.component.FxView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Scenes {
    private static Scenes INSTANCE;

    private Map<String, Stage> componentStages = new HashMap<>();
    private Map<String, Scene> componentScenes = new HashMap<>();

    public static void changeScene(String componentName) {
        if(!getInstance().componentStages.containsKey(componentName)) {
            throw new RuntimeException("Stage not found");
        }

        if(!getInstance().componentScenes.containsKey(componentName)) {
            throw new RuntimeException("Scene not found");
        }

        Stage componentStage = getInstance().componentStages.get(componentName);
        Scene componentScene = getInstance().componentScenes.get(componentName);

        componentStage.setScene(componentScene);
    }

    public void registerScene(String componentName, FxView fxView, Stage stage) {
        componentStages.put(componentName, stage);
        componentScenes.put(componentName, new Scene(fxView.getParent()));
    }

    public static Scenes getInstance() {
        return INSTANCE;
    }

    protected static void constructSingleton(Scenes scenes) {
        INSTANCE = scenes;
    }
}
