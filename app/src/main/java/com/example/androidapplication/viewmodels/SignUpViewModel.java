package com.example.androidapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapplication.CredentialValidator;
import com.example.androidapplication.validators.EmailValidator;
import com.example.androidapplication.validators.NameValidator;
import com.example.androidapplication.validators.PasswordValidator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends ViewModel {

    // Validators
    private CredentialValidator nameValidator = new NameValidator();
    private CredentialValidator emailValidator = new EmailValidator();
    private CredentialValidator passwordValidator = new PasswordValidator();

    //LiveData
    private MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    //Firebase
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //Empty constructor
    public SignUpViewModel() {
    }

    public void signUp(String name, String email, String password, String confirmPassword) {

        boolean isValidName = nameValidator.isValid(name);
        boolean isValidEmail = emailValidator.isValid(email);
        boolean isValidPassword = passwordValidator.isValid(password);
        boolean isValidConfirmPassword = password.equals(confirmPassword);

        if (!isValidName || !isValidEmail || !isValidPassword) {
            error.setValue("Email or password is incorrect");
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this::onComplete);
        }
    }

    private void onComplete(Task<AuthResult> task) {
        isRegistered.setValue(task.isSuccessful());

    }

    public MutableLiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
