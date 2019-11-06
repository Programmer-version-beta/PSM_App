package com.example.psw_app;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLookForRecipe extends Fragment {
    private static String SORT_KEY = "sort";
    private static String THRESHOLD_KEY = "threshold";
    private static String FRIDGE_KEY = "fit_fridge";

    public FragmentLookForRecipe() {
    }

    static FragmentLookForRecipe newInstance(String sort, int threshold, boolean fitFridge) {
        Bundle bundle = new Bundle();
        bundle.putString(SORT_KEY, sort);
        bundle.putInt(THRESHOLD_KEY, threshold);
        bundle.putBoolean(FRIDGE_KEY, fitFridge);
        FragmentLookForRecipe fragment= new FragmentLookForRecipe();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_look_for_recipe, container, false);
    }

}
