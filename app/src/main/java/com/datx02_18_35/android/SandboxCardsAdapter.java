package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.level.Level;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-13.
 */

public class SandboxCardsAdapter extends RecyclerView.Adapter<SandboxCardsAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Expression> dataSet;
    public ArrayList<Expression> selected = new ArrayList<Expression>();
    private ViewHolder firstSelected=null;
    private Sandbox activity;


    SandboxCardsAdapter(ArrayList<Expression> dataSet, Sandbox activity){
        this.activity=activity;
        this.dataSet=dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression1, parent,false);
        return new SandboxCardsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        holder.cardView.setTag(R.string.viewholders,holder);
        holder.cardView.setBackgroundColor(Color.WHITE);
        holder.setIsRecyclable(false);
        if(dataSet.get(position)!= null & !holder.alreadyBound){
            CardDeflator1.deflate(holder.cardView, dataSet.get(position),GameBoard.symbolMap,180,255,false);
            holder.alreadyBound=true;
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
            activity.maySelectOperator=true;
            firstSelected=(ViewHolder) view.getTag(R.string.viewholders);
            if(activity.operatorSelcted==null){
                activity.button.setText("Make " + activity.reason + "!");
                activity.button.setBackgroundColor(Color.GREEN);
            }
        }
        else if (selected.size() == 1){

            if(selected.contains(expr) & activity.operatorSelcted==null ){
                activity.maySelectOperator=false;
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
                activity.button.setText("No " + activity.reason +"(exit)");
                activity.button.setBackgroundColor(Color.RED);

            }
            else if(activity.operatorSelcted!=null){
                ExpressionFactory expressionFactory = Level.exampleLevel.getExpressionFactory();
                Expression expression = expressionFactory.createOperator(activity.operatorSelcted,selected.get(0),expr);


                //restore animations
                firstSelected.cardView.setScaleX((float) 1.00);
                firstSelected.cardView.setScaleY((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder.frame.setScaleX((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder.frame.setScaleY((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder=null;
                activity.button.setText("No " + activity.reason +"(exit)");
                activity.button.setBackgroundColor(Color.RED);


                //restore variables
                firstSelected=null;
                selected.clear();
                activity.maySelectOperator=false;
                activity.operatorSelcted=null;

                //add new expression
                dataSet.add(expression);
                notifyItemInserted(dataSet.size());
            }

        }
        else{
            Log.d("test123","too many selections in sandbox");
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        boolean alreadyBound=false;


        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }



        @Override
        public void onClick(View view) {


        }
    }
}
