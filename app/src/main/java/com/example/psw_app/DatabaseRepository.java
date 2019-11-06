package com.example.psw_app;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String Log_tag = "FirebaseQueryLiveData";
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    FirebaseQueryLiveData(DatabaseReference ref){
        this.query = ref;
    }

    @Override
    protected void onActive(){
        Log.d(Log_tag, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive(){
        Log.d(Log_tag, "onInactive");
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(Log_tag, "Can't listened to query " + query, databaseError.toException());
        }
    }
}