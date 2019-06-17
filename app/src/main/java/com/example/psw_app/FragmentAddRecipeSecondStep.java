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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class FragmentAddRecipeSecondStep extends Fragment {
    AutoCompleteTextView atvComponent;
    EditText etAmount;
    Spinner spUnit;
    Button btNext;
    ImageButton btAdd;
    RecyclerView rvComponents;
    MyComponentsDetailsRecyclerViewAdapter mAdapter;
    Recipe recipe;

    enum Units{
        kg, g, l, ml
    }

    public FragmentAddRecipeSecondStep() {
    }

    public static FragmentAddRecipeSecondStep newInstance(Recipe recipe) {
        FragmentAddRecipeSecondStep fragment = new FragmentAddRecipeSecondStep();
        fragment.recipe = recipe;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe_second_step, container, false);
        initRecyclerView(view);
        initButtons(view);
        initViews(view);
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

    void initButtons(View view) {
        btAdd = view.findViewById(R.id.add_recipe_second_step_button_add);
        btNext = view.findViewById(R.id.add_recipe_second_step_button_next);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atvComponent.getText().toString().equals("") || etAmount.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Component must have name and amount.", Toast.LENGTH_SHORT).show();
                } else {
                    recipe.appendNewComponent(atvComponent.getText().toString(), spUnit.getSelectedItem().toString(), Double.valueOf(etAmount.getText().toString()));
                    atvComponent.setText("");
                    etAmount.setText("");
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipe.getComponents().size() > 0) {
                    FragmentAddRecipeThirdStep nextFrag = FragmentAddRecipeThirdStep.newInstance(recipe);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                else
                    Toast.makeText(getContext(), "You must add at least one component.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initViews(View view) {
        etAmount = view.findViewById(R.id.add_recipe_second_step_edittext_unit);
        atvComponent = view.findViewById(R.id.add_recipe_second_step_auto_component);
        spUnit = view.findViewById(R.id.add_recipe_second_step_spinner_unit);
        spUnit.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, Units.values()));
    }

    void initRecyclerView(View view){
        rvComponents = view.findViewById(R.id.add_recipe_second_step_recycler_view);
        rvComponents.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComponents.setHasFixedSize(true);
        mAdapter = new MyComponentsDetailsRecyclerViewAdapter(recipe.getComponents());
        rvComponents.setAdapter(mAdapter);
    }
}