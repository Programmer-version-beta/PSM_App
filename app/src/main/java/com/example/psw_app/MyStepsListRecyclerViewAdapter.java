package com.example.psw_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyStepsListRecyclerViewAdapter extends RecyclerView.Adapter<MyStepsListRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<String> mValues;

    MyStepsListRecyclerViewAdapter(ArrayList<String> items) {
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
        holder.tvName.setText(mValues.get(holder.getAdapterPosition()));
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
        ImageButton btRemove;

        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_component_textview_name);
            btRemove = view.findViewById(R.id.item_component_button_delete);
        }
    }
}
