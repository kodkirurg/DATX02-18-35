package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestRulesAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;
import java.util.HashMap;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-08.
 */

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> implements View.OnClickListener {
    int currentHighestSelectedCard=0;
    Expression goal;
    ArrayList<Expression> dataSet;
    ArrayList<Integer> selected=new ArrayList<>();
    HashMap<Integer, CardView> selectedView = new HashMap<>();
    private GameBoard activity;
    private float cardHeight,cardWidth;


    GameCardAdapter(ArrayList<Expression> dataSet, GameBoard activity, float cardHWidth, float cardHeight){
        this.dataSet = dataSet;
        this.activity=activity;
        this.cardHeight=cardHeight;
        this.cardWidth=cardHWidth;
    }

    void updateBoard(final Iterable<Expression> data){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                for (Expression expression : data) {dataSet.add(expression);}
                notifyDataSetChanged();
                restoreSelections();
            }
        });

    }

    void updateGoal(Expression goal){
        this.goal=goal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //remember to check if selected and highlight on bind.
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        holder.setIsRecyclable(false);
        if(selected.contains(position)){
            setAnimations(holder.cardView);
        }
        if(null != dataSet.get(position) & !holder.alreadyBound){
            CardInflator.inflate(holder.cardView,dataSet.get(position),cardWidth,cardHeight,false);
            if(dataSet.get(position).equals(goal)){
                setVictoryAnimation(holder.cardView);
            }
            holder.alreadyBound=true;
        }
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    @Override
    public void onClick(View v) {
        ((GameBoard)activity).newSelection(dataSet.get( (int) v.getTag()), v);
        if(((GameBoard)activity).infoWindowClicked){
            ((GameBoard)activity).infoWindowClicked=false;
            ((GameBoard)activity).popupWindow.dismiss();
        }
    }

    void setSelection(Expression expression, CardView v) {
        selected.add((int) v.getTag());
        selectedView.put((int) v.getTag(),v);
        v.findViewById(R.id.card_number_text_view).setVisibility(View.VISIBLE);
        currentHighestSelectedCard++;
        ((TextView)v.findViewById(R.id.card_number_text_view)).setTag(R.id.card_number,currentHighestSelectedCard);
        ((TextView)v.findViewById(R.id.card_number_text_view)).setText(""+((TextView)v.findViewById(R.id.card_number_text_view)).getTag(R.id.card_number));


        setAnimations(v);
    }

    void resetSelection(Expression expression, CardView v) {
        v.findViewById(R.id.card_number_text_view).setVisibility(View.GONE);
        int deSelectNumber = (int)v.findViewById(R.id.card_number_text_view).getTag(R.id.card_number);
        currentHighestSelectedCard--;
        int loopStop=selected.size();
        for(int x =0 ; loopStop > x ;x++){
            CardView cardViewNumberChange = selectedView.get(selected.get(x));
            cardViewNumberChange.setVisibility(View.GONE);
            TextView textView = cardViewNumberChange.findViewById(R.id.card_number_text_view);
            int selectionNumber = (int)textView.getTag(R.id.card_number);
            if(selectionNumber>deSelectNumber){
                textView.setTag(R.id.card_number,selectionNumber-1);
                textView.setText(""+textView.getTag(R.id.card_number));
            }
           if (selected.get(x)==v.getTag()){
                selected.remove(x);
                x--;
                loopStop--;
           }
        }
        CardView cardView = selectedView.get((int) v.getTag());
        restoreAnimations(cardView);
        selectedView.remove((int)v.getTag());

    }

    void restoreSelections(){
        for(CardView view : selectedView.values()){
            restoreAnimations(view);
            TextView textView=view.findViewById(R.id.card_number_text_view);
            textView.setVisibility(View.GONE);
        }
        currentHighestSelectedCard =0;
        selected.clear();
        selectedView.clear();
        try {
            if(!activity.victory){
                Controller.getSingleton().handleAction(new RequestRulesAction(GameBoard.boardCallback, new ArrayList<Expression>()));
            }

        } catch (GameException e) {
            e.printStackTrace();
        }
    }
    void restoreAnimations(CardView cardView){
        cardView.setBackgroundColor(Color.WHITE);
        Fx.deselectAnimation(cardView.getContext(), cardView);
    }
    void setAnimations(CardView cardView){
        cardView.setBackgroundColor(Color.BLACK);
        Fx.selectAnimation(cardView.getContext(), cardView);
    }

    //TO-DO: Something cool wtih the card that made you win
    void setVictoryAnimation(CardView cardView){

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        boolean alreadyBound=false;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}

