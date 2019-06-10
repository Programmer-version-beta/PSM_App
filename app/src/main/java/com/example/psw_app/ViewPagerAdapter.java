package com.example.psw_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Recipe recipe;
    private ArrayList<Component> components;
    ViewPagerAdapter(FragmentManager fm, Recipe recipe, ArrayList<Component> components) {
        super(fm);
        this.recipe = recipe;
        this.components = components;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentSteps.newInstance(recipe);
            case 1:
                return FragmentComponents.newInstance(components);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
