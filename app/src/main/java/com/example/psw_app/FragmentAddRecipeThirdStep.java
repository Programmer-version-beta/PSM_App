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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;


public class FragmentAddRecipeThirdStep extends Fragment {
    AutoCompleteTextView atvStep;
    RecyclerView rvSteps;
    ImageButton btAdd;
    Button btAll;
    MyStepsListRecyclerViewAdapter adapter;
    Recipe recipe;

    public FragmentAddRecipeThirdStep() {
    }

    public static FragmentAddRecipeThirdStep newInstance(Recipe recipe) {
        FragmentAddRecipeThirdStep fragment = new FragmentAddRecipeThirdStep();
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
        View view = inflater.inflate(R.layout.fragment_add_recipe_third_step, container, false);
        initButtons(view);
        initViews(view);
        initRecyclerView(view);
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

    void initButtons(View view){
        btAdd = view.findViewById(R.id.add_recipe_third_step_button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atvStep.getText().toString().equals("")){
                    Toast.makeText(getContext(), "You must enter step description.", Toast.LENGTH_SHORT).show();
                } else {
                    recipe.appendNewStep(atvStep.getText().toString());
                    atvStep.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btAll = view.findViewById(R.id.add_recipe_third_step_button_all);
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipe.getSteps().size() > 0) {
                    String UserId;
                    try {
                        UserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        UserId = "defaultID";
                    }
                    recipe.setOwner(UserId);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference memberRef = mDatabase.child("Recipe");
                    String id = memberRef.push().getKey();
                    memberRef.child(Objects.requireNonNull(id)).setValue(recipe);
                    recipe.clearListAndMap();
                    FragmentStart nextFrag = FragmentStart.newInstance();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                    Toast.makeText(getContext(), "Recipe added.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "You must enter at least one step.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initRecyclerView(View view){
        rvSteps = view.findViewById(R.id.add_recipe_third_step_recycler_view);
        rvSteps.setHasFixedSize(true);
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyStepsListRecyclerViewAdapter(recipe.getSteps());
        rvSteps.setAdapter(adapter);
    }

    void initViews(View view){
        atvStep = view.findViewById(R.id.add_recipe_third_step_auto_step);
    }
}
