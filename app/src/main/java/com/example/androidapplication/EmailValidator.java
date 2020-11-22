package com.example.androidapplication;

import android.util.Patterns;

public class EmailValidator implements CredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return Patterns.EMAIL_ADDRESS.matcher(credential).matches();
    }

}
