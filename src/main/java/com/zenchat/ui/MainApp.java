package com.zenchat.ui;

import com.zenchat.ui.app.AppModule;
import com.zenchat.ui.framework.scene.Scenes;
import com.zenchat.ui.framework.module.ModuleProvider;
import com.zenchat.ui.framework.scene.StagesConfigurer;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);

        StagesConfigurer.configureStages(
                new ModuleProvider("AppModule", AppModule.componentRegistry(), primaryStage)
        );

        Scenes.changeScene("ServerAddressComponent");

        primaryStage.show();
        log.info("Application started!");
    }

    @Override
    public void stop() throws Exception {
//        System.exit(0);
    }
}
