package com.example.androidapplication.presentation.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.BuildConfig;
import com.example.androidapplication.R;
import com.example.androidapplication.presentation.SharedPref;
import com.example.androidapplication.presentation.activities.StartActivity;
import com.example.androidapplication.presentation.fragments.SignInFragment;
import com.example.androidapplication.presentation.fragments.SignUpFragment;

import java.util.Locale;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnButtonClickListener, SignUpFragment.OnClickListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    private SharedPref sharedPref = new SharedPref();
    private Configuration configuration = new Configuration();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref.init(this);

        Timber.wtf(sharedPref.getLanguage());
        if (sharedPref.getLanguage().equals("ua")) {
            setLanguage(configuration, "ua", "UA");
        }
        setContentView(R.layout.activity_main);

        //Timber initialization
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

         // getSupportFragmentManager().beginTransaction().add(R.id.container, new SignInFragment()).commit();
        startActivity(new Intent(this, StartActivity.class));

    }

    public void setLanguage(Configuration config, String language, String country) {
        Locale locale;
        locale = new Locale(language, country);
        Locale.setDefault(locale);
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
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
