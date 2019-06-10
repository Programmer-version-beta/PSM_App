package com.example.psw_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentShowRecipe extends Fragment {
    TextView tvName;
    TextView tvType;
    TextView tvDescription;
    Recipe currentRecipe;
    ViewPager viewPager;

    public FragmentShowRecipe() {
    }

    public static FragmentShowRecipe newInstance(Recipe recipe) {
        FragmentShowRecipe fragmentShowRecipe = new FragmentShowRecipe();
        fragmentShowRecipe.currentRecipe = recipe;
        return fragmentShowRecipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        tvName = view.findViewById(R.id.show_recipe_name);
        tvType = view.findViewById(R.id.show_recipe_kind);
        tvDescription = view.findViewById(R.id.show_recipe_description);
        viewPager = view.findViewById(R.id.show_recipe_view_pager);
        tvName.setText(currentRecipe.getName());
        tvType.setText(currentRecipe.getType());
        tvDescription.setText(currentRecipe.getDescription());
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), currentRecipe, currentRecipe.getComponents()));
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
}
