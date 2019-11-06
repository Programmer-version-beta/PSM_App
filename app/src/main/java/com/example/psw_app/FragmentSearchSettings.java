package com.example.psw_app;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.Objects;

public class FragmentSearchSettings extends Fragment {


    public FragmentSearchSettings() {
    }

    public static FragmentSearchSettings newInstance() {
        return new FragmentSearchSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_settings, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        Integer [] arrayThreshold = {50, 75, 80, 90, 100};
        String [] arraySort = {"ASC", "DESC", "Threshold"};
        final CheckBox chFridge = view.findViewById(R.id.search_settings_cb_first_option);
        final Spinner spThreshold = view.findViewById(R.id.search_settings_sp_threshold);
        final Spinner spSort = view.findViewById(R.id.search_settings_sp_sort);
        Button btSearch = view.findViewById(R.id.search_settings_bt);
        spThreshold.setAdapter(new ArrayAdapter<> (view.getContext(), R.layout.support_simple_spinner_dropdown_item, arrayThreshold));
        spSort.setAdapter(new ArrayAdapter<> (view.getContext(), R.layout.support_simple_spinner_dropdown_item, arraySort));
        chFridge.setChecked(true);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                Objects.requireNonNull(manager).beginTransaction().replace(R.id.nav_host_fragment,
                        FragmentLookForRecipe.newInstance(spSort.getSelectedItem().toString(),
                                Integer.parseInt(spThreshold.getSelectedItem().toString()),
                                chFridge.isSelected())).commit();
            }
        });
    }
}