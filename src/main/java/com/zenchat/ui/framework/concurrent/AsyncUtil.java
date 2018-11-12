package com.zenchat.ui.framework.concurrent;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AsyncUtil {

    public static <T> void runTask(Task<T> task) {
        Service<T> service = new Service<T>() {
            @Override
            protected Task<T> createTask() {
                return task;
            }
        };
        service.start();
    }
}
