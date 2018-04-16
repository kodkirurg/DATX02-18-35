package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;

import game.logic_game.R;

public class ScopeHolderAdapter extends RecyclerView.Adapter<ScopeHolderAdapter.ViewHolder> {
    GameBoard activity;
    private ArrayList<ArrayList<Expression>> dataSet = new ArrayList<ArrayList<Expression>>();
    private ArrayList<ArrayList<Expression>> dataSet2 = new ArrayList<ArrayList<Expression>>();
    private ArrayList<String> section = new ArrayList<String>();

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
        holder.assumptionText.setText("Assumption");
        holder.scopeText.setText(section.get(position));

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecycleView.setLayoutManager(hs_linearLayout);
        holder.childRecycleView.setHasFixedSize(false);
        InventoryAdapter inventoryChildAdapter = new InventoryAdapter(dataSet.get(position), this.activity);
        holder.childRecycleView.setAdapter(inventoryChildAdapter);

        LinearLayoutManager new_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.cardView.setLayoutManager(new_linearLayout);
        holder.cardView.setHasFixedSize(false);
        InventoryAdapter assumptionChildAdapter = new InventoryAdapter(dataSet2.get(position), this.activity);
        holder.cardView.setAdapter(assumptionChildAdapter);
    }
    public void updateInventory(final ArrayList<ArrayList<Expression>> newSet,
                                final ArrayList<String> newSection,
                                final ArrayList<ArrayList<Expression>> newSet2 ) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                section.clear();
                dataSet2.clear();
                dataSet2.addAll(newSet2);
                section.addAll(newSection);
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
        RecyclerView cardView;
        TextView assumptionText;
        TextView scopeText;


        ViewHolder(View itemView) {
            super(itemView);
            childRecycleView =  itemView.findViewById(R.id.recycler_scope);
            assumptionText = itemView.findViewById(R.id.assumption_text);
            cardView = itemView.findViewById(R.id.recycler_assumption);
            scopeText = itemView.findViewById(R.id.scope_text);
        }
    }
}
