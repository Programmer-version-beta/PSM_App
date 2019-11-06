package com.example.psw_app.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psw_app.AppViewModel;
import com.example.psw_app.FragmentAddNewRecipe;
import com.example.psw_app.MyUserProfileAdapter;
import com.example.psw_app.R;
import com.example.psw_app.Recipe;
import com.example.psw_app.SwipeToDelete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class UserProfileFragment extends Fragment {
    private MyUserProfileAdapter adapter;

    public UserProfileFragment() {
    }

    public static UserProfileFragment newInstance() {
        Bundle args = new Bundle();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView tvEmail = view.findViewById(R.id.user_profile_name);
        tvEmail.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
        RecyclerView recyclerView = view.findViewById(R.id.user_profile_recyclerView);
        adapter = new MyUserProfileAdapter(new ArrayList<Recipe>(), getFragmentManager(), appViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LiveData<DataSnapshot> liveData = appViewModel.selectAllUserRecipes();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    adapter.setArguments(dataSnapshot.getChildren());
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        initButton(view);
        return view;
    }

    private void initButton (View view) {
        FloatingActionButton fab = view.findViewById(R.id.user_profile_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                Objects.requireNonNull(manager).beginTransaction().replace(R.id.nav_host_fragment, FragmentAddNewRecipe.newInstance()).commit();
            }
        });
    }
}