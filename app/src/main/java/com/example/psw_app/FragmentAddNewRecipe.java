package com.example.psw_app;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.psw_app.ui.notifications.UserProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentAddNewRecipe extends Fragment {
    private MyComponentsListAdapter adapter;
    private AppViewModel appViewModel;
    private TextInputLayout tilName;
    private TextInputLayout tilDescription;

    public FragmentAddNewRecipe() {
    }

    public static FragmentAddNewRecipe newInstance() {
        return new FragmentAddNewRecipe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_recipe, container, false);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        initViewsAndButtons(view);
        initRecyclerView(view);
        return view;
    }

    private void initViewsAndButtons(View view) {
        tilName = view.findViewById(R.id.add_new_recipe_itl_name);
        tilDescription = view.findViewById(R.id.add_new_recipe_til_description);
        Button btConfirm = view.findViewById(R.id.add_new_recipe_bt);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()) {
                    String name = Objects.requireNonNull(tilName.getEditText()).getText().toString();
                    String description = Objects.requireNonNull(tilDescription.getEditText()).getText().toString();
                    appViewModel.insertRecipe(new Recipe(name, description, adapter.getComponents()));
                    FragmentManager manager = getFragmentManager();
                    Objects.requireNonNull(manager).beginTransaction().replace(R.id.nav_host_fragment, UserProfileFragment.newInstance()).commit();
                }
            }
        });
        FloatingActionButton fabAdd = view.findViewById(R.id.add_new_recipe_fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(getContext());
            }
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView rvComponents = view.findViewById(R.id.add_new_recipe_rv);
        rvComponents.setHasFixedSize(true);
        rvComponents.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyComponentsListAdapter(new ArrayList<Component>());
        rvComponents.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(adapter));
        itemTouchHelper.attachToRecyclerView(rvComponents);
    }

    private void showAddItemDialog(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16,8,16,8);
        final EditText etItem = new EditText(context);
        etItem.setHint("Produkt");
        final EditText etAmount = new EditText(context);
        etAmount.setHint("Ilość");
        layout.addView(etItem);
        layout.addView(etAmount);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Jakich składników potrzeba do wykonania?")
                .setMessage("Podaj nazwę oraz ilość.")
                .setView(layout)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = etItem.getText().toString();
                        String amount = etAmount.getText().toString();
                        adapter.insertItem(new Component(item, amount, appViewModel.getCurrentUserID()));
                    }
                })
                .setNegativeButton("Anuluj", null)
                .create();
        dialog.show();
    }

    private boolean validate() {
        boolean hasError = false;

        if(Objects.requireNonNull(tilName.getEditText()).getText().toString().isEmpty()) {
            tilName.setError("Field can't be empty.");
            hasError = true;
        } else {
            tilName.setError(null);
        }

        if(Objects.requireNonNull(tilDescription.getEditText()).getText().toString().isEmpty()) {
            tilDescription.setError("Field can't be empty.");
            hasError = true;
        } else {
            tilDescription.setError(null);
        }

        if(!hasError && adapter.getItemCount() == 0)
            hasError = true;

        return hasError;
    }
}