package com.example.androidapplication.presentation.validators;


public class NameValidator implements CredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return credential.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }
}
