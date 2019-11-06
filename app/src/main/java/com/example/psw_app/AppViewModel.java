package com.example.psw_app;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppViewModel extends AndroidViewModel {
    private final FirebaseQueryLiveData componentsInFridge = new FirebaseQueryLiveData(FirebaseDatabase.getInstance().getReference("Fridge"));
    private final FirebaseQueryLiveData userRecipes = new FirebaseQueryLiveData(FirebaseDatabase.getInstance().getReference("Recipe"));

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public String getCurrentUserID() {
        return FirebaseAuth.getInstance().getUid();
    }

    //Database Select//

    public ArrayList<Recipe> prepareHomeRecipeList () {
        return new ArrayList<>();
    }

    @NonNull
    public LiveData<DataSnapshot> selectAllComponents() {
        return componentsInFridge;
    }

    @NonNull
    public LiveData<DataSnapshot> selectAllUserRecipes() {
        return userRecipes;
    }

    public void prepareData() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Recipe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Recipe>> t = new GenericTypeIndicator<HashMap<String, Recipe>>() {};
                HashMap<String, Recipe> map = dataSnapshot.getValue(t);
                /*for (Recipe item : Objects.requireNonNull(map).values()) {
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
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    //Database Insert//

    public void insertComponent(Component component) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference memberRef = mDatabase.child("Fridge");
        String id = memberRef.push().getKey();
        memberRef.child(Objects.requireNonNull(id)).setValue(component);
    }

    void insertRecipe(Recipe recipe) {
        String UserId;
        try {
            UserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
            UserId = "defaultID";
        }
        recipe.setOwner(UserId);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference memberRef = mDatabase.child("Recipe");
        String id = memberRef.push().getKey();
        memberRef.child(Objects.requireNonNull(id)).setValue(recipe);
    }

    //Database delete//

    void deleteComponent(String name) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Fridge").orderByChild("name").equalTo(name);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database", "onCancelled", databaseError.toException());
            }
        });
    }

    void deleteRecipe(String name) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Recipe").orderByChild("name").equalTo(name);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database", "onCancelled", databaseError.toException());
            }
        });
    }
}