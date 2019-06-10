package com.example.psw_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentSteps extends Fragment {
    TextView tvText;
    Recipe recipe;
    public FragmentSteps() {
    }


    public static FragmentSteps newInstance(Recipe recipe) {
        FragmentSteps fragment = new FragmentSteps();
        fragment.recipe = recipe;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        tvText = view.findViewById(R.id.steps_textView);
        tvText.setText(arrayListToString(recipe.getSteps()));
        return view;
    }

    String arrayListToString(ArrayList<String> arrayList) {
        StringBuilder result = new StringBuilder("1. "+arrayList.get(0));
        for(int i = 1; i<arrayList.size(); i++) {
            result.append("\n").append(String.valueOf(i + 1)).append(". ").append(arrayList.get(i));
        }
        return result.toString();
    }
}
