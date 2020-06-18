package com.app.group15.PasswordPolicyManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordPolicyCharNotAllowedTest {

    @Test
    void isPasswordValidTest() {
        PasswordPolicyCharNotAllowedMock passwordPolicyCharNotAllowed = new PasswordPolicyCharNotAllowedMock();

        String noBannedChar0 = "abACW";
        String bannedChar1 = "$abACW";
        String noBannedChar1 = "(abACW";

        assertFalse(passwordPolicyCharNotAllowed.isPasswordValid(bannedChar1));
        assertTrue(passwordPolicyCharNotAllowed.isPasswordValid(noBannedChar1));
        assertTrue(passwordPolicyCharNotAllowed.isPasswordValid(noBannedChar0));
    }
}