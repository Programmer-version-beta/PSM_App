package com.example.psw_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyComponentsDetailsRecyclerViewAdapter extends RecyclerView.Adapter<MyComponentsDetailsRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Component> mValues;


    MyComponentsDetailsRecyclerViewAdapter(ArrayList<Component> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_component_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvName.setText(mValues.get(holder.getAdapterPosition()).getName());
        holder.tvAmount.setText(mValues.get(position).getAmount() + " " + mValues.get(position).getUnit());
        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValues.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAmount;
        ImageButton btRemove;

        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_component_details_textview_name);
            tvAmount = view.findViewById(R.id.item_component_details_textview_type);
            btRemove = view.findViewById(R.id.item_component_details_button_delete);
        }
    }
}
