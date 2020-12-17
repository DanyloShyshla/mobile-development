package com.example.androidapplication.presentation.validators;

public class PasswordValidator implements CredentialValidator {
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Override
    public boolean isValid(String credential) {
        return credential.length() >= PASSWORD_MIN_LENGTH;
    }
}
