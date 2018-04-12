package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;

import game.logic_game.R;

public class ScopeHolderAdapter extends RecyclerView.Adapter<ScopeHolderAdapter.ViewHolder> {
    GameBoard activity;
    private ArrayList<ArrayList<Expression>> dataSet = new ArrayList<ArrayList<Expression>>();

    public ScopeHolderAdapter(GameBoard activity){
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ScopeHolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecycleView.setLayoutManager(hs_linearLayout);
        holder.childRecycleView.setHasFixedSize(true);
        InventoryAdapter inventoryChildAdapter = new InventoryAdapter(dataSet.get(position), this.activity);
        holder.childRecycleView.setAdapter(inventoryChildAdapter);
    }
    public void updateInventory(final ArrayList<ArrayList<Expression>> newSet) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                dataSet.addAll(newSet);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataSet.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView childRecycleView;


        ViewHolder(View itemView) {
            super(itemView);
            childRecycleView =  itemView.findViewById(R.id.inv_recycler_row);
        }
    }
}
