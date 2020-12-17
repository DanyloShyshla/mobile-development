package com.example.androidapplication.presentation.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapplication.presentation.validators.CredentialValidator;
import com.example.androidapplication.presentation.validators.EmailValidator;
import com.example.androidapplication.presentation.validators.PasswordValidator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import timber.log.Timber;

public class SignInViewModel extends ViewModel {
    private CredentialValidator emailValidator = new EmailValidator();
    private CredentialValidator passwordValidator = new PasswordValidator();

    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // Empty constructor
    public SignInViewModel() {

    }

    public void signIn(String email, String password) {
        boolean isValidEmail = emailValidator.isValid(email);
        boolean isValidPassword = passwordValidator.isValid(password);

        if (!isValidEmail || !isValidPassword) {
            error.setValue("Email or password is incorrect");
            //return;
        } else {
            Timber.i(email + " " + password);

            // Sign in logic with Firebase Auth
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                    isLoggedIn.setValue(task.isSuccessful()));
        }


    }

    public MutableLiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    private void onComplete(Task<AuthResult> task) {
        isLoggedIn.setValue(task.isSuccessful());

    }
}
