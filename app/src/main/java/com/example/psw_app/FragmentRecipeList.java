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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FragmentRecipeList extends Fragment {
    ArrayList<String> listOfUserComponents;
    ArrayList<Recipe> recipes;
    MyRecipeListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    TextView tvString;

    public FragmentRecipeList() {
    }

    public static FragmentRecipeList newInstance(ArrayList<String> list) {
        FragmentRecipeList fragment = new FragmentRecipeList();
        fragment.listOfUserComponents = list;
        fragment.recipes = new ArrayList<>();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipelist_list, container, false);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recipe_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyRecipeListRecyclerViewAdapter(recipes, getActivity());
        recyclerView.setAdapter(adapter);
        tvString = view.findViewById(R.id.recipe_list_textView);
        prepareData();
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

    void prepareData() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Recipe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Recipe>> t = new GenericTypeIndicator<HashMap<String, Recipe>>() {};
                HashMap<String, Recipe> map = dataSnapshot.getValue(t);
                for (Recipe item : Objects.requireNonNull(map).values()) {
                    for(Component item2: item.getComponents()){
                        if(listOfUserComponents.contains(item2.getName())){
                            item.have++;
                        }
                        else
                            item.dontHave++;
                    }
                    if(item.have > item.dontHave)
                        recipes.add(item);
                    adapter.notifyDataSetChanged();
                    tvString.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
