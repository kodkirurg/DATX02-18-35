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
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.OperatorType;
import com.datx02_18_35.model.game.Level;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-13.
 */

public class SandboxCardsAdapter extends RecyclerView.Adapter<SandboxCardsAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Expression> dataSet;
    public static ArrayList<Expression> selected = new ArrayList<Expression>();
    private ViewHolder firstSelected=null;


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
        holder.cardView.setTag(R.string.viewholders,holder);
        holder.cardView.setBackgroundColor(Color.WHITE);
        holder.setIsRecyclable(false);
        if(dataSet.get(position)!= null & !holder.alreadyBound){
            CardDeflator.deflate(holder.cardView, dataSet.get(position),GameBoard.symbolMap);
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
            Sandbox.maySelectOperator=true;
            firstSelected=(ViewHolder) view.getTag(R.string.viewholders);
            if(Sandbox.operatorSelcted==null){
                Sandbox.button.setText("Make " + Sandbox.reason + "!");
                Sandbox.button.setBackgroundColor(Color.GREEN);
            }
        }
        else if (selected.size() == 1){

            if(selected.contains(expr) & Sandbox.operatorSelcted==null ){
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
                Sandbox.button.setText("No " + Sandbox.reason +"(exit)");
                Sandbox.button.setBackgroundColor(Color.RED);

            }
            else if(Sandbox.operatorSelcted!=null){
                ExpressionFactory expressionFactory = Level.exampleLevel.getExpressionFactory();
                Expression expression = expressionFactory.createOperator(Sandbox.operatorSelcted,selected.get(0),expr);


                //restore animations
                firstSelected.cardView.setScaleX((float) 1.00);
                firstSelected.cardView.setScaleY((float) 1.00);
                SandboxOperatorAdapter.previousSelectedOperatorHolder.frame.setScaleX((float) 1.00);
                SandboxOperatorAdapter.previousSelectedOperatorHolder.frame.setScaleY((float) 1.00);
                SandboxOperatorAdapter.previousSelectedOperatorHolder=null;
                Sandbox.button.setText("No " + Sandbox.reason +"(exit)");
                Sandbox.button.setBackgroundColor(Color.RED);


                //restore variables
                firstSelected=null;
                selected.clear();
                Sandbox.maySelectOperator=false;
                Sandbox.operatorSelcted=null;

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
