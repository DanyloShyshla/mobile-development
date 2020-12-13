package com.example.androidapplication.validators;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidapplication.R;
import com.example.androidapplication.StartActivity;
import com.example.androidapplication.fragments.SignInFragment;
import com.example.androidapplication.fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnButtonClickListener, SignUpFragment.OnClickListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container, new SignInFragment()).commit();

    }

    //Sign in
    @Override
    public void onSignInButtonClicked() {
        startActivity(new Intent(this, StartActivity.class));
    }

    //Redirecting to sign up
    @Override
    public void onSignUpRedirectionClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, signUpFragment).commit();
    }

    //Sign up
    @Override
    public void onSignUpButtonClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, signInFragment).commit();
    }

    //Return to sign in
    @Override
    public void onBackButtonClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, signInFragment).commit();
    }

}
