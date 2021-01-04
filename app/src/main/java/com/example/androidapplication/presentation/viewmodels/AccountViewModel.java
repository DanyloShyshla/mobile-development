package com.example.androidapplication.presentation.viewmodels;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapplication.presentation.validators.CredentialValidator;
import com.example.androidapplication.presentation.validators.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;

public class AccountViewModel extends ViewModel {

    //Validators
    private final CredentialValidator passwordValidator = new PasswordValidator();

    //LiveData
    private final MutableLiveData<Boolean> isAccountEdited = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    //Firebase
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //Empty constructor
    public AccountViewModel() {

    }

    @SuppressLint("TimberArgCount")
    public void updatePassword(String password) {

        boolean isValidPassword = passwordValidator.isValid(password);
        Timber.wtf("Password is valid: ", isValidPassword);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Timber.wtf("Firebase user email: ", firebaseUser.getEmail());

        if (firebaseUser != null && isValidPassword) {
            firebaseUser.updatePassword(password).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    isAccountEdited.setValue(task.isSuccessful());
                } else {
                    error.setValue("Password was not updated");
                }
            });
        } else {
            error.setValue("Firebase user or password is incorrect");
        }
    }

    public MutableLiveData<Boolean> getIsAccountEdited() {
        return isAccountEdited;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
