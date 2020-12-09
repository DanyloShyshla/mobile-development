package com.example.androidapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplication.BuildConfig;
import com.example.androidapplication.R;
import com.example.androidapplication.viewmodels.SignInViewModel;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class SignInFragment extends Fragment {
    private SignInViewModel viewModel;
    private EditText emailAddress;
    private EditText password;
    private EditText confirmPassword;
    private Button signInButton;
    private TextView signUp;
    private TextView signUpRedirection;
    private OnButtonClickListener onButtonClickListener;

    //Empty constructor
    public SignInFragment() {
    }

    public interface OnButtonClickListener {

        void onSignInButtonClicked();

        void onSignUpRedirectionClicked();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onButtonClickListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSignUpNavClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onButtonClickListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void fieldInitialization(View rootView) {
        EditText emailAddress = rootView.findViewById(R.id.email);
        EditText password = rootView.findViewById(R.id.password);
        Button signInButton = rootView.findViewById(R.id.singInButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signUp.setOnClickListener(onSignUpClickListener);
        fieldInitialization(rootView);
        registerViewModel();
        onButtonClicked();

        return rootView;
    }

    private final View.OnClickListener onSignUpClickListener = view -> {
        onButtonClickListener.onSignUpRedirectionClicked();
    };

    private void onButtonClicked() {
        signInButton.setOnClickListener(view -> {
            String emailValue = emailAddress.getText().toString();
            String passwordValue = password.getText().toString();

            viewModel.signIn(emailValue, passwordValue);
        });
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
//        viewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
//            if (isLoggedIn) {
//                showMessage("Successfully logged in");
//
//            } else
//                showMessage("Failed to log in");
//
//        });
        viewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                showMessage("Successfully logged in");
                onButtonClickListener.onSignInButtonClicked();

            } else
                showMessage("Failed to log in");
        });

        viewModel.getError().observe(this, this::showMessage);
    }


    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}