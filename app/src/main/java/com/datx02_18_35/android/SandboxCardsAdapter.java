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
    public ViewHolder previousSelectedOperatorHolder=null;
    private ViewHolder firstSelected=null;
    private Sandbox activity;
    float cardWidth, cardHeight;


    SandboxCardsAdapter(ArrayList<Expression> dataSet, Sandbox activity, float cardWidth, float cardHeight){
        this.activity=activity;
        this.dataSet=dataSet;
        this.cardHeight=cardHeight;
        this.cardWidth=cardWidth;
    }


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
        holder.cardView.setBackgroundColor(Color.GRAY);
        holder.setIsRecyclable(false);
        if(dataSet.get(position)!= null & !holder.alreadyBound){
            CardInflator.inflate(holder.cardView, dataSet.get(position),cardWidth,cardHeight,false);
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
        ViewHolder holder = (SandboxCardsAdapter.ViewHolder) view.getTag(R.string.viewholders);
        if(selected.size() == 0){
            selected.add(expr);
            Fx.selectAnimation(view.getContext(),view);
            activity.maySelectOperator=true;
            firstSelected=(ViewHolder) view.getTag(R.string.viewholders);
            if(activity.operatorSelcted==null){
                activity.button.setText("Make " + activity.reason + "!");
                activity.button.setBackgroundColor(Color.GREEN);
            }
            previousSelectedOperatorHolder=holder;
        }
        else if (selected.size() == 1){

            if(selected.contains(expr) & activity.operatorSelcted==null ){
                activity.maySelectOperator=false;
                Fx.deselectAnimation(view.getContext(),view);
                ArrayList<Expression> newList = new ArrayList<>();
                previousSelectedOperatorHolder=null;
                selected=newList;
                activity.button.setText("No " + activity.reason +"(exit)");
                activity.button.setBackgroundColor(Color.RED);

            }
            else if(activity.operatorSelcted!=null){
                ExpressionFactory expressionFactory =GameBoard.level.expressionFactory;
                Expression expression = expressionFactory.createOperator(activity.operatorSelcted,selected.get(0),expr);


                //restore animations
                firstSelected.cardView.setScaleX((float) 1.00);
                firstSelected.cardView.setScaleY((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder.frame.setScaleX((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder.frame.setScaleY((float) 1.00);
                activity.adapterRight.previousSelectedOperatorHolder=null;
                activity.button.setText("No " + activity.reason +"(exit)");
                activity.button.setBackgroundColor(Color.RED);

                Fx.deselectAnimation(previousSelectedOperatorHolder.cardView.getContext(),previousSelectedOperatorHolder.cardView);
                //restore variables
                firstSelected=null;
                selected.clear();
                activity.maySelectOperator=false;
                activity.operatorSelcted=null;

                //add new expression
                dataSet.add(expression);
                notifyItemInserted(dataSet.size());
                //scroll to last pos when new card added
                activity.recyclerViewLeft.smoothScrollToPosition(dataSet.size()-1);
            }
            else{
                ArrayList<Expression> newSelection = new ArrayList<Expression>();
                if(previousSelectedOperatorHolder!=null){
                    //un-select other operator previously selected
                    Fx.deselectAnimation(previousSelectedOperatorHolder.cardView.getContext(),previousSelectedOperatorHolder.cardView);
                    Fx.selectAnimation(holder.cardView.getContext(),holder.cardView);
                }
                previousSelectedOperatorHolder=holder;
                newSelection.add(expr);
                selected=newSelection;
                firstSelected=holder;
            }

        }
        else{
            Log.d("test123","too many selections in sandbox");
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        boolean alreadyBound=false;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}
