package com.example.psw_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FragmentStart extends Fragment {

    public FragmentStart() {
    }

    public static FragmentStart newInstance() {
        return new FragmentStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initButtons(view);
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

    public void initButtons(View view) {
        Button btAddRecipe = view.findViewById(R.id.button_add);
        Button btLookRecipe = view.findViewById(R.id.button_look);
        Button btMyProfile = view.findViewById(R.id.button_my_profile);
        Button btSignIn = view.findViewById(R.id.button_sign_in);
        btAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddRecipeBasic nextFrag= new FragmentAddRecipeBasic();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        btLookRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLookForRecipe nextFrag= FragmentLookForRecipe.newInstance();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        btMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null)
                    Toast.makeText(getContext(), "Please sign in.", Toast.LENGTH_LONG).show();
                else{
                    FragmentUserProfile nextFrag= FragmentUserProfile.newInstance();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame1, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null)
                    Toast.makeText(getContext(), "You are already logged.", Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
