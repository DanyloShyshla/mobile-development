package com.example.androidapplication.validators;

import android.util.Patterns;

import com.example.androidapplication.CredentialValidator;

public class EmailValidator implements CredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return Patterns.EMAIL_ADDRESS.matcher(credential).matches();
    }

}
