package com.example.psw_app;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentShowRecipe extends Fragment {
    private Recipe currentRecipe;

    private FragmentShowRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
    }

    static FragmentShowRecipe newInstance(Recipe recipe) {
        return new FragmentShowRecipe(recipe);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        TextView tvName = view.findViewById(R.id.show_recipe_tv_name);
        TextView tvComponentsList = view.findViewById(R.id.show_recipe_tv_components_list);
        TextView tvStepsList = view.findViewById(R.id.show_recipe_tv_steps_list);
        tvName.setText(currentRecipe.getName());
        tvStepsList.setText(currentRecipe.getDescription());
        tvComponentsList.setText(currentRecipe.listToString());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
