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

public class MainActivity extends AppCompatActivity {

    private EditText emailAddress;
    private EditText password;
    private TextInputLayout inputLayoutEmailAddress;
    private TextInputLayout inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button signInButton = findViewById(R.id.singInButton);
        inputLayoutEmailAddress = findViewById(R.id.emailInputLayout);
        inputLayoutPassword = findViewById(R.id.passwordInputLayout);
        TextView signUpRedirection = findViewById(R.id.signUpRedirectionText);

        /*Filed validation*/

        EmailValidator emailValidator = new EmailValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        signInButton.setOnClickListener(v -> {
            String emailValue = emailAddress.getText().toString();
            String passwordValue = password.getText().toString();
            String message = "Success";

            if (!emailValidator.isValid(emailValue)) {
                inputLayoutEmailAddress.setError("Enter a valid email");
            } else {
                inputLayoutEmailAddress.setErrorEnabled(false);
            }

            if (!passwordValidator.isValid(passwordValue)) {
                inputLayoutPassword.setError("Enter a valid password");
            } else {
                inputLayoutPassword.setErrorEnabled(false);
            }

            if (emailValidator.isValid(emailValue) && passwordValidator.isValid(passwordValue)) {
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        /*Sign up redirection text*/

        String redirection = "Don't have an account? Sign Up";
        SpannableString spannableString = new SpannableString(redirection);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent myIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(myIntent);
            }
        };
        spannableString.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUpRedirection.setText(spannableString);
        signUpRedirection.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
