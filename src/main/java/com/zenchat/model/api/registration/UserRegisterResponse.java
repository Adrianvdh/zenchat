package com.zenchat.model.api.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserRegisterResponse implements Serializable {
    private String name;
    private String username;
}
