package com.example.androidapplication.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidapplication.R;
import com.example.androidapplication.presentation.OnItemClick;
import com.example.androidapplication.presentation.adapters.PhotoAdapter;

public class PhotoDetailsFragment extends Fragment {

    private RecyclerView photoList;
    private OnItemClick onItemClickListener;

    public PhotoDetailsFragment(RecyclerView photoList) {
        // Required empty public constructor

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnItemClick) {
            onItemClickListener = (OnItemClick) context;
        } else {
            throw new IllegalStateException("Activity must implement OnItemClick interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_photo_details, container, false);

        initUI(root);

        return root;
    }

    private void initUI(View root) {
        photoList = root.findViewById(R.id.photo_data);
        photoList.setAdapter(new PhotoAdapter(onItemClickListener));
        photoList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}