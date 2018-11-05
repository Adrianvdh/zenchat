package com.zenchat.ui.framework.module;

import com.zenchat.ui.framework.component.ComponentRegistry;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModuleProvider {
    private String moduleName;
    private ComponentRegistry componentRegistry;
    private Stage stage;
}
