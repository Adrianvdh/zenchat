package com.zenchat.ui.app.registration;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationValidatorTest {

    @Test
    public void validateName_nameValid_expectPass() {
        String name = "adrian";

        Assert.assertTrue(RegistrationValidator.validateName(name));
    }

    @Test
    public void validateName_nameContactsNumber_expectFail() {
        String name = "adrian123";

        Assert.assertFalse(RegistrationValidator.validateName(name));
    }

    @Test
    public void validateName_nameToShort_expectFail() {
        String name = "l";

        Assert.assertFalse(RegistrationValidator.validateName(name));
    }

    @Test
    public void validateName_nameToLong_expectFail() {
        String name = "adrianvandenhoutenandmysecondnameisbilly";

        Assert.assertFalse(RegistrationValidator.validateName(name));
    }

    @Test
    public void validateUsername_validUsername_expectPass() {
        String username = "adrian1";

        Assert.assertTrue(RegistrationValidator.validateUserName(username));
    }

    @Test
    public void validateUsername_invalidUsernameWithSpecialCharacters_expectFail() {
        String username = "adrian1(";

        Assert.assertFalse(RegistrationValidator.validateUserName(username));
    }

    @Test
    public void validateUserName_usernameToShort_expectFail() {
        String username = "l";

        Assert.assertFalse(RegistrationValidator.validateUserName(username));
    }

    @Test
    public void validateUserName_usernameToLong_expectFail() {
        String name = "adriansusername1234";

        Assert.assertFalse(RegistrationValidator.validateName(name));
    }


    @Test
    public void validatePassword_validPassword_expectPass() {
        String password = "Test1234";

        Assert.assertTrue(RegistrationValidator.validatePassword(password));
    }

    @Test
    public void validatePassword_invalidPasswordToLong_expectFail() {
        String password = "Test1234**&&*12222222222222222222222222222222";

        Assert.assertFalse(RegistrationValidator.validatePassword(password));
    }

    @Test
    public void validatePassword_invalidPasswordToShort_expectFail() {
        String password = "test";

        Assert.assertFalse(RegistrationValidator.validatePassword(password));
    }


}