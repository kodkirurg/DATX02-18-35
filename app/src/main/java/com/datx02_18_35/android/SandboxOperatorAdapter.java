package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-13.
 */

public class SandboxOperatorAdapter extends RecyclerView.Adapter<SandboxOperatorAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<OperatorType> dataSet;
    public ViewHolder previousSelectedOperatorHolder=null;
    Sandbox activity;
    float cardWidth, cardHeight;


    public SandboxOperatorAdapter(ArrayList<OperatorType> dataSet, Sandbox activity, float cardWidth, float cardHeight){
        this.dataSet=dataSet;
        this.activity=activity;
        this.cardHeight=cardHeight;
        this.cardWidth=cardWidth;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frame = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_operator, parent,false);
        return new SandboxOperatorAdapter.ViewHolder(frame);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.frame.setOnClickListener(this);
        holder.frame.setTag(position);
        holder.frame.setTag(R.string.viewholders,holder);
        holder.frame.getLayoutParams().width=Math.round(Tools.convertDpToPixel(cardWidth));
        holder.frame.getLayoutParams().height=Math.round(Tools.convertDpToPixel(cardHeight));
        holder.frame.setBackgroundColor(Color.WHITE);
        ImageView imageView = holder.frame.findViewById(R.id.operator_imageview);
        if (dataSet.get(position) != null){
            switch (dataSet.get(position)) {
                case IMPLICATION:
                    Tools.setImage(imageView,R.drawable.vertical_implication);
                    break;
                case DISJUNCTION:
                    Tools.setImage(imageView,R.drawable.vertical_disjunction);
                    break;
                case CONJUNCTION:
                    Tools.setImage(imageView,R.drawable.vertical_conjunction);
                    break;
                default:
                    Log.d("test123", "onBindViewHolder in sanbox: Unknown rule, wtf?");
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    @Override
    public void onClick(View view) {
        //get position in viewholder from view
        int pos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        //get viewholder
        ViewHolder holder = (SandboxOperatorAdapter.ViewHolder) view.getTag(R.string.viewholders);
        if(dataSet.get(pos) == activity.operatorSelcted){
            //Un-select
            activity.operatorSelcted=null;
            activity.button.setText("Make assumption!");
            activity.button.setBackgroundColor(Color.GREEN);
            previousSelectedOperatorHolder=null;
            holder.frame.setScaleX((float) 1.00);
            holder.frame.setScaleY((float) 1.00);
        }
        else if (activity.maySelectOperator){
            //set new other operator selection
            holder.frame.setScaleX((float) 1.05);
            holder.frame.setScaleY((float) 1.05);
            activity.button.setText("No assumption(exit)");
            activity.button.setBackgroundColor(Color.RED);
            activity.operatorSelcted=dataSet.get(pos);
            if(previousSelectedOperatorHolder!=null){
                //un-select other operator previously selected
                previousSelectedOperatorHolder.frame.setScaleX((float) 1.00);
                previousSelectedOperatorHolder.frame.setScaleY((float) 1.00);
            }
            previousSelectedOperatorHolder=holder;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
