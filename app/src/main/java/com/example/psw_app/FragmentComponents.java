package com.example.psw_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentComponents extends Fragment {
    TextView tvText;
    ArrayList<Component> components;

    public FragmentComponents() {
    }


    public static FragmentComponents newInstance(ArrayList<Component> components) {
        FragmentComponents fragment = new FragmentComponents();
        fragment.components = components;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_components, container, false);
        tvText = view.findViewById(R.id.components_textView);
        tvText.setText(arrayListToString(components));
        return view;
    }

    String arrayListToString(ArrayList<Component> arrayList) {
        StringBuilder result = new StringBuilder("1. "+ arrayList.get(0).getName() + " " + arrayList.get(0).getAmount() + " " + arrayList.get(0).getUnit());
        for(int i = 1; i<arrayList.size(); i++) {
            result.append("\n").append(String.valueOf(i + 1)).append(". ").append(arrayList.get(i).getName()).append(" ").append(arrayList.get(i).getAmount()).append(" ").append(arrayList.get(i).getUnit());
        }
        return result.toString();
    }
}
