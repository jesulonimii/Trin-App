package com.erlite.feedhub.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.erlite.feedhub.R;
import com.erlite.feedhub.SharedPrefs;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ThemeBottomSheet extends BottomSheetDialogFragment {

    private RadioGroup btnGroup;
    private RadioButton darkBtn, lightBtn, systemBtn, batteryBtn;
    SharedPrefs sharedPrefs;


    public ThemeBottomSheet() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme_bottom_sheet, container, false);

        final Activity activity = getActivity();
        final Context context = getActivity().getApplicationContext();


        sharedPrefs = new SharedPrefs(context);

        btnGroup = view.findViewById(R.id.themeRdbGroup);
        darkBtn = view.findViewById(R.id.rdbDark);
        lightBtn = view.findViewById(R.id.rdbLight);
        systemBtn = view.findViewById(R.id.rdbFollowSystem);
        batteryBtn = view.findViewById(R.id.rdbBatteryAuto);

        int getTheme = sharedPrefs.themeSettings;

            //set button state
            switch (getTheme){

                case AppCompatDelegate.MODE_NIGHT_YES:
                    darkBtn.setChecked(true);
                    lightBtn.setChecked(false);
                    batteryBtn.setChecked(false);
                    systemBtn.setChecked(false);
                    break;
                case AppCompatDelegate.MODE_NIGHT_NO:
                    darkBtn.setChecked(false);
                    lightBtn.setChecked(true);
                    batteryBtn.setChecked(false);
                    systemBtn.setChecked(false);
                    break;
                case AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY:
                    darkBtn.setChecked(false);
                    lightBtn.setChecked(false);
                    batteryBtn.setChecked(true);
                    systemBtn.setChecked(false);
                    break;
                case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                    darkBtn.setChecked(false);
                    lightBtn.setChecked(false);
                    batteryBtn.setChecked(false);
                    systemBtn.setChecked(true);
                    break;

            }




       btnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {

               switch (btnGroup.getCheckedRadioButtonId()){

                   case R.id.rdbDark:
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                       sharedPrefs.saveTheme(context);
                       activity.recreate();
                       break;
                   case R.id.rdbLight:
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                       sharedPrefs.saveTheme(context);
                       activity.recreate();
                       break;
                   case R.id.rdbBatteryAuto:
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                       sharedPrefs.saveTheme(context);
                       break;
                   case R.id.rdbFollowSystem:
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                       sharedPrefs.saveTheme(context);
                       break;

               }


               dismiss();
           }
       });





        return view;
    }




}
