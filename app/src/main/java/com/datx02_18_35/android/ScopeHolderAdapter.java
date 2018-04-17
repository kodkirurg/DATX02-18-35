package com.datx02_18_35.android;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datx02_18_35.model.Util;
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
        if(position<=1){
            ConstraintSet set = new ConstraintSet();

            set.clone(holder.layout);
            // The following breaks the connection.
            set.clear(R.id.inventory_right_side, ConstraintSet.LEFT);
            set.clear(R.id.inventory_left_side);
            // Comment out line above and uncomment line below to make the connection.
            set.connect(R.id.inventory_right_side, ConstraintSet.LEFT, R.id.inventory_bottom, ConstraintSet.LEFT, 0);
            set.applyTo(holder.layout);
            holder.leftContainer.setVisibility(View.INVISIBLE);

        }
        else {
            LinearLayoutManager new_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
            holder.cardView.setLayoutManager(new_linearLayout);
            holder.cardView.setHasFixedSize(false);
            InventoryAdapter assumptionChildAdapter = new InventoryAdapter(dataSet2.get(position), this.activity);
            holder.cardView.setAdapter(assumptionChildAdapter);
        }

        holder.assumptionText.setText("Assumption");
        holder.scopeText.setText(section.get(position));

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecycleView.setLayoutManager(hs_linearLayout);
        holder.childRecycleView.setHasFixedSize(false);
        InventoryAdapter inventoryChildAdapter = new InventoryAdapter(dataSet.get(position), this.activity);
        holder.childRecycleView.setAdapter(inventoryChildAdapter);
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
        return (section.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView childRecycleView;
        RecyclerView cardView;
        TextView assumptionText;
        TextView scopeText;
        LinearLayout leftContainer;
        LinearLayout rightContainer;
        ConstraintLayout layout;


        ViewHolder(View itemView) {
            super(itemView);
            childRecycleView =  itemView.findViewById(R.id.recycler_scope);
            assumptionText = itemView.findViewById(R.id.assumption_text);
            cardView = itemView.findViewById(R.id.recycler_assumption);
            scopeText = itemView.findViewById(R.id.scope_text);
            leftContainer = itemView.findViewById(R.id.inventory_left_side);
            rightContainer = itemView.findViewById(R.id.inventory_right_side);
            layout = itemView.findViewById(R.id.inventory_bottom);
        }
    }
}
