package com.example.androidapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class StartActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        String message = "Welcome";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Info");
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Timber.i("Successful log in");

    }

    @Override
    public void onBackPressed() {
    }
}
