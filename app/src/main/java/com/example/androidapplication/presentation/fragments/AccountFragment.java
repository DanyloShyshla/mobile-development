package com.example.androidapplication.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidapplication.R;
import com.example.androidapplication.presentation.viewmodels.AccountViewModel;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {

    //UI
    EditText passwordEditText;
    Button editAccountButton;

    //OnClickListeners
    private OnButtonClickListener onButtonClickListener;

    //ViewModels
    private AccountViewModel accountViewModel;

    //Empty constructor
    public AccountFragment() {

    }

    //Interface
    public interface OnButtonClickListener {
        void onEditAccountButtonClicked();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        //Initialise UI
        editAccountButton = rootView.findViewById(R.id.editAccountButton);
        passwordEditText = rootView.findViewById(R.id.password);

        //Initialise Toolbar
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        initViewModels();

        editAccountButton.setOnClickListener(onClickListener);

        return rootView;
    }

    private void initViewModels() {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getIsAccountEdited().observe(getViewLifecycleOwner(), isAccountEdited -> {
            if (isAccountEdited) {
                onButtonClickListener.onEditAccountButtonClicked();
            }
        });
        accountViewModel.getError().observe(getViewLifecycleOwner(), this::showMessage);
    }

    // Messages handling
    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private final View.OnClickListener onClickListener = view -> {
        String password = passwordEditText.getText().toString();
        accountViewModel.updatePassword(password);
    };

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

}
