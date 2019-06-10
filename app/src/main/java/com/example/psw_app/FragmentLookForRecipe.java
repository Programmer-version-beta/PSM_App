package com.example.psw_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentLookForRecipe extends Fragment {
    private AutoCompleteTextView tvComponent;
    private MyComponentsListRecyclerViewAdapter adapter;
    private ArrayList<String> components;

    public FragmentLookForRecipe() {
        components = new ArrayList<>();
    }

    public static FragmentLookForRecipe newInstance() {
        FragmentLookForRecipe fragmentLookForRecipe = new FragmentLookForRecipe();
        fragmentLookForRecipe.components = new ArrayList<>();
        return fragmentLookForRecipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_look_for_recipe, container, false);
        tvComponent = view.findViewById(R.id.look_tv_component);
        initRecyclerView(view);
        initButtons(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void initRecyclerView(View view) {
        RecyclerView rvComponents = view.findViewById(R.id.look_recycler_view);
        rvComponents.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComponents.setHasFixedSize(true);
        adapter = new MyComponentsListRecyclerViewAdapter(components);
        rvComponents.setAdapter(adapter);
    }

    public void initButtons(View view) {
        Button btThatsAll = view.findViewById(R.id.look_button_all);
        ImageButton btAdd = view.findViewById(R.id.look_button_add);
        btThatsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(components.size() > 0) {
                    FragmentRecipeList nextFrag = FragmentRecipeList.newInstance(components);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                else
                    Toast.makeText(getContext(), "Please enter at least one product.", Toast.LENGTH_LONG).show();
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvComponent.getText().toString().equals("")) {
                    components.add(tvComponent.getText().toString());
                    adapter.notifyDataSetChanged();
                    tvComponent.setText("");
                }
                else{
                    Toast.makeText(getContext(), "Please enter name of product.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
