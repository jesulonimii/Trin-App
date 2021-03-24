package com.erlite.feedhub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erlite.feedhub.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AppInfoBottomSheet extends BottomSheetDialogFragment {

    public AppInfoBottomSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_bottom_sheet, container, false);

        return view;
    }
}
