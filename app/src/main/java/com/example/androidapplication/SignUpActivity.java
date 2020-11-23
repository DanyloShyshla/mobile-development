package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText emailAddress;
    private EditText password;
    private EditText confirmPassword;
    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;
    private Button signUpButton;
    private final CredentialValidator emailValidator = new EmailValidator();
    private final CredentialValidator passwordValidator = new PasswordValidator();
    private final CredentialValidator nameValidator = new NameValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        fieldInitialization();
        fieldValidation();

    }

    private void fieldValidation() {
        signUpButton.setOnClickListener(v -> {
            String nameValue = name.getText().toString();
            String emailValue = emailAddress.getText().toString();
            String passwordValue = password.getText().toString();
            String confirmationPasswordValue = confirmPassword.getText().toString();
            String message = "Success";
            boolean validation = true;

            if (!nameValidator.isValid(nameValue)) {
                nameInputLayout.setError("Enter a valid name");
                validation = false;
            } else {
                nameInputLayout.setErrorEnabled(false);
            }

            if (!emailValidator.isValid(emailValue)) {
                emailInputLayout.setError("Enter a valid email");
                validation = false;
            } else {
                emailInputLayout.setErrorEnabled(false);
            }

            if (!passwordValidator.isValid(passwordValue)) {
                passwordInputLayout.setError("Enter a valid password");
                validation = false;
            } else {
                passwordInputLayout.setErrorEnabled(false);
            }

            if (!confirmationPasswordValue.equals(passwordValue)) {
                confirmPasswordInputLayout.setError("Passwords are not equal");
                validation = false;
            } else {
                confirmPasswordInputLayout.setErrorEnabled(false);
            }

            if (validation) {
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fieldInitialization() {
        name = findViewById(R.id.name);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        signUpButton = findViewById(R.id.signUpButton);
    }
}