package com.example.psw_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class FragmentUserProfile extends Fragment {
    ImageView ivPhoto;
    TextView tvEmail;
    RecyclerView recyclerView;
    ArrayList<Recipe> recipes;
    MyUserProfileAdapter adapter;

    public FragmentUserProfile() {
        recipes = new ArrayList<>();
    }

    public static FragmentUserProfile newInstance() {
        FragmentUserProfile fragmentUserProfile = new FragmentUserProfile();
        fragmentUserProfile.recipes = new ArrayList<>();
        return fragmentUserProfile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ivPhoto = view.findViewById(R.id.user_profile_imageView);
        tvEmail = view.findViewById(R.id.user_profile_email);
        tvEmail.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
        recyclerView = view.findViewById(R.id.user_profile_recyclerView);
        adapter = new MyUserProfileAdapter(recipes, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        prepareData();
        return view;
    }

    void prepareData() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Recipe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Recipe>> t = new GenericTypeIndicator<HashMap<String, Recipe>>() {};
                HashMap<String, Recipe> map = dataSnapshot.getValue(t);
                for (Recipe item : Objects.requireNonNull(map).values()) {
                    if(item.getOwner().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())){
                        recipes.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
