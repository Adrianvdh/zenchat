package com.zenchat.model.api.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginUserRequest implements Serializable {
    private String username;
    private String password;
}
