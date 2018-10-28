package com.zenchat.common.messaging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Headers implements Serializable {

    private Map<String, String> headers = new HashMap<>();

    public Headers() {
    }

    public void addHeader(String name, String value) {
        if(this.headers != null) {
            this.headers.put(name, value);
        }
    }

    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }

    public String get(String name) {
        if(!containsHeader(name)) {
            throw new HeadersException(String.format("Header '%s' could not be found!"));
        }
        return headers.get(name);
    }
}
