package com.example.psw_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyNewRecipeListAdapter extends RecyclerView.Adapter<MyNewRecipeListAdapter.ViewHolder> {
    private ArrayList<Recipe> mValues;
    private FragmentManager fragmentManager;

    public MyNewRecipeListAdapter(ArrayList<Recipe> items, FragmentManager fragmentManager) {
        mValues = items;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvName.setText(mValues.get(holder.getAdapterPosition()).getName());
        holder.tvKind.setText(mValues.get(holder.getAdapterPosition()).getDescription());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, FragmentShowRecipe.newInstance(mValues.get(holder.getAdapterPosition())))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvName;
        TextView tvKind;
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.item_recipe_name);
            tvKind = view.findViewById(R.id.item_recipe_kind);
            imageView = view.findViewById(R.id.item_recipe_iv);
        }
    }
}
