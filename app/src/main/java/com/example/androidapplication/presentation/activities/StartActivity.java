package com.example.androidapplication.presentation.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidapplication.R;
import com.example.androidapplication.presentation.NetworkChangeReceiver;
import com.example.androidapplication.presentation.OnItemClick;
import com.example.androidapplication.presentation.SharedPref;
import com.example.androidapplication.presentation.adapters.PhotoAdapter;
import com.example.androidapplication.presentation.fragments.AccountFragment;
import com.example.androidapplication.presentation.uidata.PhotoViewData;
import com.example.androidapplication.presentation.viewmodels.PhotoViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

import timber.log.Timber;

public class StartActivity extends AppCompatActivity implements AccountFragment.OnButtonClickListener, OnItemClick {

    private final PhotoAdapter photoAdapter = new PhotoAdapter();
    private PhotoViewModel photoViewModel;
    private final Configuration configuration = new Configuration();
    private final SharedPref sharedPref = new SharedPref();
    private final NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    private String TAG;

    //UI
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar loadingIndicator;

    //Fragments
    private final AccountFragment accountFragment = new AccountFragment();

    /*    private PhotoViewData photoViewData;*/


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sharedPref.init(this);
        /*Timber.wtf(sharedPref.getLanguage());
        if (sharedPref.getLanguage().equals("ua")) {
            setLanguage(configuration, "ua", "UA");
        }*/
        sharedPref.setUserName("hello228");

        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        initialiseToolbar();
        initUI();
        initRecycler();
        initViewModel();


    }



    private void initialiseToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Timber.wtf("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.start_activity_menu, menu);
        /*return super.onCreateOptionsMenu(menu);*/
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_language:
                Toast.makeText(this, "Change language", Toast.LENGTH_SHORT).show();
                changeLanguage();
                return true;
            case R.id.edit_account:
                Toast.makeText(this, "Edit account", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().add(R.id.activity_start, accountFragment).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void changeLanguage() {
        String currentLanguage = sharedPref.getLanguage();
        final String language = "ua";
        final String country = "UA";
        if (currentLanguage.equals(language)) {
            Timber.wtf("Current language equals language");
            this.getApplicationContext().getResources().updateConfiguration(configuration, null);
            //setLanguage(configuration, "us","US");
            sharedPref.setLanguage("us");
        } else {
            setLanguage(configuration, language, country);
            sharedPref.setLanguage(language);
        }
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void initUI() {
        //Refresh layout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> photoViewModel.loadPhotoData());

        //Loading indicator
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void initViewModel() {
        photoViewModel = new PhotoViewModel();

        photoViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            hideLoading();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(errorMessage);
            builder.setTitle("Error");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        photoViewModel.getResponse().observe(this, response -> {
            photoAdapter.setItems(response);
            hideLoading();
        });


        photoViewModel.loadPhotoData();
    }


    private void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        loadingIndicator.setVisibility(View.GONE);
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.photo_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onBackPressed() {
    }

    public void setLanguage(Configuration config, String language, String country) {
        Locale locale;
        locale = new Locale(language, country);
        Locale.setDefault(locale);
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public void onEditAccountButtonClicked() {
        finish();
    }

    @Override
    public void onItemClick(PhotoViewData photoViewData) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("photo", photoViewData);
        startActivity(intent);
        Toast.makeText(this, "New intent", Toast.LENGTH_SHORT).show();
    }
}
