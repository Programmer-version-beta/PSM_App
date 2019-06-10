package com.example.psw_app;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecipeListRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeListRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Recipe> mValues;
    private FragmentActivity parentActivity;

    MyRecipeListRecyclerViewAdapter(ArrayList<Recipe> items, FragmentActivity parentActivity) {
        mValues = items;
        this.parentActivity = parentActivity;
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
        holder.tvKind.setText(mValues.get(holder.getAdapterPosition()).getType());
        holder.tvNumber.setText(mValues.get(position).have+"/"+((mValues.get(position).have + mValues.get(position).dontHave)));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentShowRecipe nextFrag= FragmentShowRecipe.newInstance(mValues.get(holder.getAdapterPosition()));
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, nextFrag, "findThisFragment")
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
        TextView tvNumber;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.item_recipe_name);
            tvKind = view.findViewById(R.id.item_recipe_kind);
            tvNumber = view.findViewById(R.id.item_recipe_number);
        }
    }
}
