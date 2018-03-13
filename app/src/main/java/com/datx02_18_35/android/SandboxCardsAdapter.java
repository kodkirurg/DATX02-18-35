package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-13.
 */

public class SandboxCardsAdapter extends RecyclerView.Adapter<SandboxCardsAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    private ArrayList<Expression> dataSet;
    private ArrayList<Expression> selected = new ArrayList<Expression>();


    public SandboxCardsAdapter(ArrayList<Expression> dataSet){this.dataSet=dataSet;}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new SandboxCardsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        holder.cardView.setBackgroundColor(Color.WHITE);
        if(dataSet.get(position)!= null){
            new GameCardAdapter.CardDeflator(holder.cardView, dataSet.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    @Override
    public void onClick(View view) {
        int position = (int)view.getTag();
        Expression expr = dataSet.get(position);
        if(selected.size() == 0){
            selected.add(expr);
            view.setScaleX((float) 1.05);
            view.setScaleY((float) 1.05 );
            Sandbox.maySelectOperator=true;
        }
        else if (selected.size() == 1){

            if(selected.contains(expr)){
                Sandbox.maySelectOperator=false;
                view.setScaleX((float) 1.00);
                view.setScaleY((float) 1.00 );
                ArrayList<Expression> newList = new ArrayList<>();
                //de-selection and remove from list
                for (Expression item : selected ){
                    if(item.equals(expr)){
                        continue;
                    }
                    newList.add(item);
                }
                selected=newList;
            }

        }
        else{
            Log.d("test123","too many selections in sandbox");
        }

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder{
        CardView cardView;


        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }


        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }

        @Override
        public void onClick(View view) {


        }
    }
}
