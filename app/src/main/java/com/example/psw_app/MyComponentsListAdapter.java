package com.example.psw_app;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyComponentsListAdapter extends RecyclerView.Adapter<MyComponentsListAdapter.ViewHolder> {
    private final ArrayList<Component> mValues;

    MyComponentsListAdapter(ArrayList<Component> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_component, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvName.setText(mValues.get(holder.getAdapterPosition()).getName());
        holder.tvAmount.setText(String.valueOf(mValues.get(holder.getAdapterPosition()).getAmount()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    void insertItem(Component component) {
        mValues.add(component);
        notifyDataSetChanged();
    }

    void deleteItem(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    ArrayList<Component> getComponents() {
        return mValues;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAmount;

        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_component_tv_name);
            tvAmount = view.findViewById(R.id.item_component_tv_amount);
        }
    }
}