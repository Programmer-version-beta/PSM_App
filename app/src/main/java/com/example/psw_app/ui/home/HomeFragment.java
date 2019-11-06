package com.example.psw_app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psw_app.AppViewModel;
import com.example.psw_app.FragmentSearchSettings;
import com.example.psw_app.MyNewRecipeListAdapter;
import com.example.psw_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private AppViewModel appViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initRecyclerView(root);
        initButtons(root);
        return root;
    }

    private void initRecyclerView(View view) {
        RecyclerView rvRecipes = view.findViewById(R.id.home_rv);
        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        MyNewRecipeListAdapter adapter = new MyNewRecipeListAdapter(appViewModel.prepareHomeRecipeList(), getFragmentManager());
        rvRecipes.setAdapter(adapter);
    }

    private void initButtons(View view) {
        FloatingActionButton fabSearch = view.findViewById(R.id.home_fab_look_recipe);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                Objects.requireNonNull(manager).beginTransaction().replace(R.id.nav_host_fragment, FragmentSearchSettings.newInstance()).commit();
            }
        });
    }
}