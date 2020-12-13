package com.example.androidapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplication.R;
import com.example.androidapplication.viewmodels.SignInViewModel;

import org.jetbrains.annotations.NotNull;


public class SignInFragment extends Fragment {
    private SignInViewModel viewModel;
    private EditText emailAddress;
    private EditText password;
    private EditText confirmPassword;
    private Button signInButton;
    private TextView signUp;
    private OnButtonClickListener onButtonClickListener;

    //Empty constructor
    public SignInFragment() {
    }

    //Callback interface
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
    }

    private void fieldInitialization(View rootView) {
        emailAddress = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        signInButton = rootView.findViewById(R.id.singInButton);
        signUp = rootView.findViewById(R.id.signUp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        fieldInitialization(rootView);
        signUp.setOnClickListener(onSignUpClickListener);
        signInButton.setOnClickListener(onSignInButtonClicked);
        registerViewModel();
        return rootView;
    }

    private final View.OnClickListener onSignUpClickListener = view -> {
        onButtonClickListener.onSignUpRedirectionClicked();
    };

    private final View.OnClickListener onSignInButtonClicked = view -> {
        String emailValue = emailAddress.getText().toString();
        String passwordValue = password.getText().toString();
        viewModel.signIn(emailValue, passwordValue);
    };

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        viewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                showMessage("Successfully signed in");
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