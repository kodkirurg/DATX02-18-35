package com.datx02_18_35.android;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by raxxor on 2018-02-11.
 */

public class EditItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final RecyclerAdapter recyclerAdapter;
    int viewHolder, target;

    public EditItemTouchHelperCallback(RecyclerAdapter adapter) {
        recyclerAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN ;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {

        //display target set
        target.itemView.setBackgroundColor(Color.GREEN);
        //remove previous target
        if(this.target !=target.getAdapterPosition()){
            recyclerView.findViewHolderForAdapterPosition(this.target).itemView.setBackgroundColor(Color.BLUE);
        }
        //set new index targets
        this.viewHolder = viewHolder.getAdapterPosition();
        this.target = target.getAdapterPosition();

        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //Called by the ItemTouchHelper when the user interaction with an element is over and it also completed its animation.
        super.clearView(recyclerView, viewHolder);
        //if valid target then move to it
        if(this.viewHolder >= 0 && this.target >= 0){
            recyclerAdapter.onItemMove(this.viewHolder, this.target);
        }
        //clear selection of indexs
        this.viewHolder = -1;
        this.target = -1;
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        //add fake "holder" for the place we are moving from or solve deselecting targets


    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        recyclerAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
