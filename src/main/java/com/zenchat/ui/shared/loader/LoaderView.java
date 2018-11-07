package com.zenchat.ui.shared.loader;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoaderView {
    private Node parentNode;
    private ParallelTransition transition;

    public LoaderView() {
        init();
    }

    public void init() {

        Rectangle rectangle = new Rectangle(0, 0, 40, 40);
        rectangle.setFill(Color.BLUE);
        parentNode = rectangle;

        RotateTransition rotate = new RotateTransition(Duration.millis(750));
        rotate.setToAngle(360);

        transition = new ParallelTransition(rectangle, rotate);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    public void stop() {
        transition.stop();
    }

    public Node getParent() {
        return parentNode;
    }
}
