package com.example.androidapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapplication.R;
import com.example.androidapplication.viewmodels.SignUpViewModel;

import org.jetbrains.annotations.NotNull;

public class SignUpFragment extends Fragment {

    private SignUpViewModel viewModel;
    private EditText name;
    private EditText emailAddress;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;
    private OnClickListener onClickListener;

    //Empty constructor
    public SignUpFragment() {
    }

    //Callback interface
    public interface OnClickListener {

        void onBackButtonClicked();

        void onSignUpButtonClicked();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onClickListener = (OnClickListener) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString() + " must implement OnSignUpClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fieldInitialization(View rootView) {
        name = rootView.findViewById(R.id.name);
        emailAddress = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        confirmPassword = rootView.findViewById(R.id.confirmPassword);
        signUpButton = rootView.findViewById(R.id.signUpButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        fieldInitialization(rootView);
        registerViewModel();
        signUpButton.setOnClickListener(onSignUpClickListener);
        onReturnBackClicked(rootView);

        return rootView;
    }

    private final View.OnClickListener onSignUpClickListener = view -> {
        String nameValue = name.getText().toString();
        String emailValue = emailAddress.getText().toString();
        String passwordValue = password.getText().toString();
        String confirmPasswordValue = confirmPassword.getText().toString();

        viewModel.signUp(nameValue, emailValue, passwordValue, confirmPasswordValue);
    };

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.getIsRegistered().observe(getViewLifecycleOwner(), isRegistered -> {
            showMessage("Successfully signed up");
            onClickListener.onSignUpButtonClicked();
        });
        viewModel.getError().observe(this, this::showMessage);
    }

    private void onReturnBackClicked(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(view -> onClickListener.onBackButtonClicked());
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}