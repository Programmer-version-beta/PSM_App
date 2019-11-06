package com.example.psw_app;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MyComponentsAdapter extends RecyclerView.Adapter<MyComponentsAdapter.ViewHolder> {
    private final ArrayList<Component> mValues;
    private AppViewModel appViewModel;

    public MyComponentsAdapter(ArrayList<Component> items, AppViewModel appViewModel) {
        mValues = items;
        this.appViewModel = appViewModel;
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
        holder.tvAmount.setText(String.valueOf(mValues.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    void deleteItem(int position) {
        appViewModel.deleteComponent(mValues.get(position).getName());
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void setArguments(Iterable<DataSnapshot> children) {
        mValues.clear();
        for(DataSnapshot readData: children) {
            mValues.add(readData.getValue(Component.class));
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAmount;

        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.item_component_details_textview_name);
            tvAmount = view.findViewById(R.id.item_component_details_textview_type);
        }
    }
}
