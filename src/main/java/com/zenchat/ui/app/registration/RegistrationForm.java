package com.zenchat.ui.app.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
