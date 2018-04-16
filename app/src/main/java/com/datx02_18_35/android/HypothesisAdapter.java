package com.datx02_18_35.android;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;

import game.logic_game.R;

public class HypothesisAdapter extends RecyclerView.Adapter<HypothesisAdapter.ViewHolder> {
    GameBoard activity;
    private ArrayList<ArrayList<Expression>> dataSet = new ArrayList<ArrayList<Expression>>();
    private ArrayList<String> section = new ArrayList<String>();

    public HypothesisAdapter(GameBoard activity){
        this.activity = activity;
    }

    @Override
    public HypothesisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_2, parent, false);
        return new HypothesisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HypothesisAdapter.ViewHolder holder, int position) {
        holder.hypothesisText.setText(section.get(position));
        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.hypothesisRec.setLayoutManager(hs_linearLayout);
        holder.hypothesisRec.setHasFixedSize(false);
        InventoryAdapter inventoryChildAdapter = new InventoryAdapter(dataSet.get(position), this.activity);
        holder.hypothesisRec.setAdapter(inventoryChildAdapter);
    }
    public void updateHypothesis(final ArrayList<ArrayList<Expression>> newSet, final ArrayList<String> newSection) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                section.clear();
                section.addAll(newSection);
                dataSet.addAll(newSet);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView hypothesisRec;
        TextView hypothesisText;


        ViewHolder(View itemView) {
            super(itemView);
            hypothesisRec =  itemView.findViewById(R.id.recycler_hypothesis);
            hypothesisText = itemView.findViewById(R.id.hypothesis_text);
        }
    }
}
