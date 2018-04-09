package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestApplyRuleAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.rules.Rule;

import java.util.ArrayList;
import java.util.Collection;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-01.
 */

public class GameRuleAdapter extends RecyclerView.Adapter<GameRuleAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Rule> dataSet;
    GameBoard activity;
    float cardWidth,cardHeight;


    GameRuleAdapter(ArrayList<Rule> dataSet, GameBoard activity,float cardWidth, float cardHeight){
        this.dataSet=dataSet;
        this.activity=activity;
        this.cardHeight=cardHeight;
        this.cardWidth=cardWidth;
    }


    void updateBoard(final Collection<Rule> data){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                dataSet.addAll(data);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public GameRuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frame = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rule, parent,false);
        return new GameRuleAdapter.ViewHolder(frame);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //set click listener
        holder.frame.setOnClickListener(this);
        holder.frame.setTag(position);

        //set size
        holder.frame.getLayoutParams().width=Math.round(Tools.convertDpToPixel(cardWidth));
        holder.frame.getLayoutParams().height=Math.round(Tools.convertDpToPixel(cardHeight));

        //set visuals
        ImageView imageView = holder.frame.findViewById(R.id.rule_imageview);
        if (dataSet.get(position) != null){
            switch (dataSet.get(position).type) {
                case CONJUNCTION_INTRODUCTION:
                    Tools.setImage(imageView,R.drawable.conjunction_introduction);
                    break;
                case ABSURDITY_ELIMINATION:
                    Tools.setImage(imageView,R.drawable.absurdity_elimination);
                    break;
                case CONJUNCTION_ELIMINATION:
                    Tools.setImage(imageView,R.drawable.conjunction_elimination);
                    break;
                case IMPLICATION_INTRODUCTION:
                    Tools.setImage(imageView,R.drawable.implication_introduction);
                    break;
                case DISJUNCTION_ELIMINATION:
                    Tools.setImage(imageView,R.drawable.disjunction_elimination);
                    break;
                case DISJUNCTION_INTRODUCTION:
                    Tools.setImage(imageView,R.drawable.disjunction_introduction);
                    break;
                case IMPLICATION_ELIMINATION:
                    Tools.setImage(imageView,R.drawable.implication_elimination);
                    break;
                case LAW_OF_EXCLUDED_MIDDLE:
                    Tools.setImage(imageView,R.drawable.excluded_middle);
                    break;
                default:
                    Log.d("test123", "onBindViewHolder: Unknown rule, wtf?");
            }
        }

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View v) {
        try {
            Controller.getSingleton().handleAction(new RequestApplyRuleAction(GameBoard.boardCallback, dataSet.get((int)v.getTag())) );
            activity.adapterLeft.restoreSelections();
            if(((GameBoard)activity).infoWindowClicked){
                ((GameBoard)activity).infoWindowClicked=false;
                ((GameBoard)activity).popupWindow.dismiss();
            }
        } catch (GameException e) {
            e.printStackTrace();
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FrameLayout frame;


        ViewHolder(FrameLayout itemView) {
            super(itemView);
            frame = itemView;
        }


        @Override
        public void onClick(View view) {


        }
    }

}
