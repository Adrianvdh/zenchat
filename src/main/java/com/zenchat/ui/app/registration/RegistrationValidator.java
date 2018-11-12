package com.zenchat.ui.app.registration;

import lombok.Getter;

@Getter
public class RegistrationValidator {

    private static final String NAME_REGEX_PATTERN = "[a-zA-Z]+";
    private static final String USERNAME_REGEX_PATTERN = "[a-zA-Z]+[0-9]*";
    private static final String PASSWORD_REGEX_PATTERN = "[a-zA-Z]+[0-9]*";

    public static boolean validateName(String name) {
        return !name.isEmpty() && (name.length() <= 30 && name.length() >= 2) && name.matches(NAME_REGEX_PATTERN);
    }

    public static boolean validateUserName(String username) {
        return !username.isEmpty() && (username.length() <= 15 && username.length() >= 4) && username.matches(USERNAME_REGEX_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return !password.isEmpty() && (password.length() < 30 && password.length() >= 8) && password.matches(PASSWORD_REGEX_PATTERN);
    }


}
