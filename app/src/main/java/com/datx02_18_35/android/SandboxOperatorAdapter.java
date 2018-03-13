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

public class SandboxOperatorAdapter extends RecyclerView.Adapter<SandboxOperatorAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    private ArrayList<OperatorType> dataSet;

    public SandboxOperatorAdapter(ArrayList<OperatorType> dataSet){
        this.dataSet=dataSet;
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
        holder.frame.setBackgroundColor(Color.WHITE);

        //set visuals
        ImageView imageView = holder.frame.findViewById(R.id.operator_imageview);
        if (dataSet.get(position) != null){
            switch (dataSet.get(position)) {
                case IMPLICATION:
                    imageView.setBackgroundResource(R.drawable.vertical_implication);
                    break;
                case DISJUNCTION:
                    imageView.setBackgroundResource(R.drawable.vertical_disjunction);
                    break;
                case CONJUNCTION:
                    imageView.setBackgroundResource(R.drawable.vertical_conjunction);
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
    public void onClick(View view) {

        if(Sandbox.maySelectOperator){
            if(dataSet.get((int)view.getTag())==Sandbox.operatorSelcted){
                Sandbox.operatorSelcted=null;
                view.setScaleX((float) 1.00);
                view.setScaleY((float) 1.00);
                return;
            }
            if(Sandbox.operatorIndex!=-1){
                view.findViewWithTag(Sandbox.operatorIndex).setScaleX((float) 1.00);
                view.findViewWithTag(Sandbox.operatorIndex).setScaleY((float) 1.00);
            }
            Sandbox.operatorSelcted = dataSet.get((int)view.getTag());
            //animations
            view.setScaleX((float) 1.05);
            view.setScaleY((float) 1.05);

        }
        else {
            Log.d("test123", "onClick: "+ "operator selection error");
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
        FrameLayout frame;


        ViewHolder(FrameLayout itemView) {
            super(itemView);
            frame = itemView;
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
