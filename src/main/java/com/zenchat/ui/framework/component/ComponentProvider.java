package com.zenchat.ui.framework.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComponentProvider {
    private String componentName;
    private Component component;
}
