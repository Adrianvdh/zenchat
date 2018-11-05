package com.zenchat.ui.framework.component;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ComponentRegistry {

    private List<ComponentProvider> componentProviders;

    private ComponentRegistry(List<ComponentProvider> componentProviders) {
        this.componentProviders = componentProviders;
    }

    public static ComponentRegistry fromComponentProviders(ComponentProvider... providers) {
        return new ComponentRegistry(Arrays.asList(providers));
    }
}
