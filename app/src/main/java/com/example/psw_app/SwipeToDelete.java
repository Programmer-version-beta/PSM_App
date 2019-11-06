package com.example.psw_app;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback {
    private MyComponentsAdapter mAdapter;
    private MyComponentsListAdapter mAdapter2;
    private MyUserProfileAdapter mAdapter3;
    private int adapterNumber;

    public SwipeToDelete(MyComponentsAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.adapterNumber = 1;
    }

    SwipeToDelete(MyComponentsListAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter2 = adapter;
        this.adapterNumber = 2;
    }

    public SwipeToDelete(MyUserProfileAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter3 = adapter;
        this.adapterNumber = 3;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        switch(adapterNumber) {
            case 1:
                mAdapter.deleteItem(position);
                break;
            case 2:
                mAdapter2.deleteItem(position);
                break;
            case 3:
                mAdapter3.deleteItem(position);
                break;
            default:
                break;
        }
    }
}
