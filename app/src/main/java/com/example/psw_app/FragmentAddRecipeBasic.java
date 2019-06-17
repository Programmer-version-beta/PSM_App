package com.example.psw_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class FragmentAddRecipeBasic extends Fragment {
    EditText etName;
    Spinner spType;
    EditText etDescription;
    Button btNext;
    Recipe recipe;

    enum Types{
        Italian, Polish, German, Spanish, Other
    }

    public FragmentAddRecipeBasic() {
    }

    public static FragmentAddRecipeBasic newInstance() {
        return new FragmentAddRecipeBasic();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe_basic, container, false);
        initTexts(view);
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

    void initTexts(View view){
        etName = view.findViewById(R.id.add_recipe_basic_edittext_name);
        spType = view.findViewById(R.id.add_recipe_basic_spinner_type);
        etDescription = view.findViewById(R.id.add_recipe_basic_edittext_description);
        spType.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, Types.values()));
    }

    void initButtons(View view) {
        btNext = view.findViewById(R.id.add_recipe_basic_button_next);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etName.getText().toString().equals("")) {
                    recipe = new Recipe();
                    recipe.setName(etName.getText().toString());
                    recipe.setType(spType.getSelectedItem().toString());
                    recipe.setDescription(etDescription.getText().toString());
                    FragmentAddRecipeSecondStep nextFrag = FragmentAddRecipeSecondStep.newInstance(recipe);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                else
                    Toast.makeText(getContext(), "You must enter at least name.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}