package com.example.androidapplication.presentation.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidapplication.BuildConfig;
import com.example.androidapplication.R;
import com.example.androidapplication.presentation.ConnectionStateListener;
import com.example.androidapplication.presentation.SharedPref;
import com.example.androidapplication.presentation.activities.StartActivity;
import com.example.androidapplication.presentation.fragments.SignInFragment;
import com.example.androidapplication.presentation.fragments.SignUpFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnButtonClickListener, SignUpFragment.OnClickListener {

    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;

    private SharedPref sharedPref = new SharedPref();
    private Configuration configuration = new Configuration();
    public final String KEY = "KEY";
    private String TAG;


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

        initFirebase();

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container, new SignInFragment()).commit();
//        startActivity(new Intent(this, StartActivity.class));

    }

    private void initFirebase() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Timber.tag(TAG).w(task.getException(), "Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Timber.tag(TAG).d(token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setConnectionMonitoring() {
        ConnectionStateListener connectionStateMonitor = new ConnectionStateListener(this);
        final String dialogTitle = "Notification";
        final String dialogMessage = "Network connection was lost";

        connectionStateMonitor.observe(this, isNetwork -> {
            if (!isNetwork) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(dialogMessage);
                builder.setTitle(dialogTitle);
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
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

        if (getIntent().getExtras().getString(KEY) == null) {
            Timber.wtf("onSignInButtonClicked Intent value is null ---- Rendering regular activity");
            startActivity(new Intent(this, StartActivity.class));
        } else {
            // TODO: 05.01.2021 write code here
        }

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
