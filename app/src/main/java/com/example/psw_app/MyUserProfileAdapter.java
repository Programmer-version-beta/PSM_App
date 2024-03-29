package com.example.psw_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MyUserProfileAdapter extends RecyclerView.Adapter<MyUserProfileAdapter.ViewHolder> {
    private ArrayList<Recipe> mValues;
    private FragmentManager fragmentManager;
    private AppViewModel appViewModel;

    public MyUserProfileAdapter(ArrayList<Recipe> items, FragmentManager fragmentManager, AppViewModel appViewModel) {
        mValues = items;
        this.fragmentManager = fragmentManager;
        this.appViewModel = appViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvName.setText(mValues.get(holder.getAdapterPosition()).getName());
        holder.tvKind.setText(mValues.get(holder.getAdapterPosition()).getDescription());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, FragmentShowRecipe.newInstance(mValues.get(position)))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    void deleteItem(int position) {
        appViewModel.deleteRecipe(mValues.get(position).getName());
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void setArguments(Iterable<DataSnapshot> children) {
        mValues.clear();
        for(DataSnapshot readData: children) {
            mValues.add(readData.getValue(Recipe.class));
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvName;
        TextView tvKind;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.item_recipe_name);
            tvKind = view.findViewById(R.id.item_recipe_kind);
        }
    }
}
