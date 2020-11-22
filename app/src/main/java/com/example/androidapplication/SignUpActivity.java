package com.example.androidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView signUpRedirection = findViewById(R.id.signUpRedirectionText);

        /*Filed validation*/

        NameValidator nameValidator = new NameValidator();
        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        signUpButton.setOnClickListener(v -> {
            String nameValue = name.getText().toString();
            String emailValue = emailAddress.getText().toString();
            String passwordValue = password.getText().toString();
            String confirmationPasswordValue = confirmPassword.getText().toString();
            String message = "Success";

            if (!nameValidator.isValid(nameValue)) {
                nameInputLayout.setError("Enter a valid name");
            } else {
                nameInputLayout.setErrorEnabled(false);
            }

            if (!emailValidator.isValid(emailValue)) {
                emailInputLayout.setError("Enter a valid email");
            } else {
                emailInputLayout.setErrorEnabled(false);
            }

            if (!passwordValidator.isValid(passwordValue)) {
                passwordInputLayout.setError("Enter a valid password");
            } else {
                passwordInputLayout.setErrorEnabled(false);
            }

            if (!confirmationPasswordValue.equals(passwordValue)) {
                confirmPasswordInputLayout.setError("Passwords are not equal");
            } else {
                confirmPasswordInputLayout.setErrorEnabled(false);
            }

            if (
                    emailValidator.isValid(emailValue) &&
                    passwordValidator.isValid(passwordValue) &&
                    nameValidator.isValid(nameValue) &&
                    passwordValue.equals(confirmationPasswordValue)
            ) {
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        /*Sign up redirection text*/

        String redirection = "Return to the Sign In";
        SpannableString spannableString = new SpannableString(redirection);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        };
        spannableString.setSpan(clickableSpan, 14, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUpRedirection.setText(spannableString);
        signUpRedirection.setMovementMethod(LinkMovementMethod.getInstance());
    }
}