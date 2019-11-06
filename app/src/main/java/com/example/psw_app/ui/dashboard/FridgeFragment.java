package com.example.psw_app.ui.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psw_app.AppViewModel;
import com.example.psw_app.MyComponentsAdapter;
import com.example.psw_app.R;
import com.example.psw_app.SwipeToDelete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.psw_app.Component;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;


public class FridgeFragment extends Fragment {
    private AppViewModel appViewModel;
    private MyComponentsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fridge, container, false);
        initRecyclerView(root);
        initButtons(root);
        return root;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.fridge_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyComponentsAdapter(new ArrayList<Component>(), appViewModel);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        LiveData<DataSnapshot> liveData = appViewModel.selectAllComponents();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    adapter.setArguments(dataSnapshot.getChildren());
                }
            }
        });
    }

    private void initButtons(View view) {
        FloatingActionButton fabAdd = view.findViewById(R.id.fridge_fab_add_item);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(getActivity());
            }
        });
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
                .setTitle("Dodaj nowy produkt do lodówki")
                .setMessage("Co kupiłeś?")
                .setView(layout)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = etItem.getText().toString();
                        String amount = etAmount.getText().toString();
                        appViewModel.insertComponent(new Component(item, amount, appViewModel.getCurrentUserID()));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .create();
        dialog.show();
    }
}