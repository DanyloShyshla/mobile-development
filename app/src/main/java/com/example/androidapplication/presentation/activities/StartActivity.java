package com.example.androidapplication.presentation.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.presentation.adapters.PhotoAdapter;
import com.example.androidapplication.presentation.viewmodels.PhotoViewModel;

import timber.log.Timber;

public class StartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter = new PhotoAdapter();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        String message = "Welcome";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Welcoming message");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        initRecycler();
        initViewModel();

    }

    private void initViewModel() {
        //PhotoViewModel photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        PhotoViewModel photoViewModel = new PhotoViewModel();

        photoViewModel.getErrorMessage().observe(this, errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show());

        photoViewModel.getResponse().observe(this, response ->
                photoAdapter.setItems(response));

        photoViewModel.loadPhotoData();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.photo_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onBackPressed() {
    }
}
