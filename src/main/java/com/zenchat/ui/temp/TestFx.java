package com.zenchat.ui.temp;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 500);


        Rectangle r = new Rectangle(0, 0, 40, 40);
        r.setFill(Color.BLUE);
        root.getChildren().add(r);
        StackPane.setAlignment(r,Pos.CENTER); //set it to the Center Left(by default it's on the center)


//        TranslateTransition translate =
//                new TranslateTransition(Duration.millis(750));
//        translate.setToX(390);
//        translate.setToY(390);
//
//        FillTransition fill = new FillTransition(Duration.millis(750));
//        fill.setToValue(Color.RED);

        RotateTransition rotate = new RotateTransition(Duration.millis(750));
        rotate.setToAngle(360);

//        ScaleTransition scale = new ScaleTransition(Duration.millis(750));
//        scale.setToX(0.1);
//        scale.setToY(0.1);

//        ParallelTransition transition = new ParallelTransition(r, translate, fill, rotate, scale);
        ParallelTransition transition = new ParallelTransition(r, rotate);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();

        primaryStage.setTitle("JavaFX Scene Graph Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
