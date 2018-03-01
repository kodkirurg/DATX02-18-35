package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datx02_18_35.lib.logicmodel.expression.Absurdity;
import com.datx02_18_35.lib.logicmodel.expression.Conjunction;
import com.datx02_18_35.lib.logicmodel.expression.Disjunction;
import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.Implication;
import com.datx02_18_35.lib.logicmodel.expression.Operator;
import com.datx02_18_35.lib.logicmodel.expression.Proposition;

import java.util.ArrayList;
import java.util.Collections;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-08.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    public ArrayList<Expression> dataSet;
    public boolean rules;


    RecyclerAdapter(ArrayList<Expression> dataSet){
        this.dataSet = dataSet;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       new CardDeflator(holder,dataSet.get(position));
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public boolean onItemMove(int indexFrom, int indexTo) {
        Collections.swap(dataSet,indexFrom,indexTo);
        notifyItemMoved(indexFrom,indexTo);
        //implement
        return true;
    }

    public void onItemDismiss(int adapterPosition) {
        //implement
    }

    @Override
    public void onClick(View v) {
        Log.d("test123","test ");
        int test = (int) v.getTag();
        Log.d("test123","" + test);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder{
        public CardView cardView;


        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }

        @Override
        public void onClick(View view) {

            view.setBackgroundColor(Color.BLACK);
        }
    }

    private static class CardDeflator{
        CardView topCardView;
        final String dots = " .. ";

        
        CardDeflator(ViewHolder holder, Expression expr){
            topCardView = holder.cardView;


            //whole card one symbol
            if(expr instanceof Proposition | expr instanceof Absurdity){
                Log.d("test123", "test");
                topCardView.removeAllViews();
                TextView text = new TextView(topCardView.getContext());
                text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                text.setGravity(Gravity.CENTER);
                text.setText(expr.toString());
                text.setTextSize(40);
                text.setTextColor(Color.BLUE);
                topCardView.addView(text);
            }
            else {
                Operator op = (Operator) expr;
                Expression op1 = op.getOperand1();
                Expression op2 = op.getOperand2();


                topCardView.findViewById(R.id.card_frame_middle).setBackgroundColor(Color.WHITE);
                ImageView middleImage = topCardView.findViewById(R.id.card_image_middle);
                if(op instanceof Implication){
                    middleImage.setBackgroundResource(R.drawable.vertical_implication);
                }
                else if(op instanceof Disjunction){
                    middleImage.setBackgroundResource(R.drawable.horizontal_disjunction);
                }
                else if(op instanceof Conjunction){
                    middleImage.setBackgroundResource(R.drawable.horizontal_conjunction);
                }

                // set big operator in middle + no complex on up/down card.
                if ((op1 instanceof Proposition | op1 instanceof Absurdity) &  (op2 instanceof Proposition | op2 instanceof Absurdity) ){
                    rmView(R.id.card_frame_lower);
                    rmView(R.id.card_frame_upper);
                    rmView(R.id.card_card_1_1);
                    rmView(R.id.card_card_2_4);

                    mParent(R.id.card_card_1_2);
                    mParent(R.id.card_card_2_3);


                    sText(R.id.card_text_2,op1.toString());
                    sText(R.id.card_text_3,op2.toString());
                }
                else{
                    ImageView upperImage = topCardView.findViewById(R.id.card_image_upper);
                    ImageView lowerImage = topCardView.findViewById(R.id.card_image_lower);
                    topCardView.findViewById(R.id.card_frame_lower).setBackgroundColor(Color.WHITE);
                    topCardView.findViewById(R.id.card_frame_upper).setBackgroundColor(Color.WHITE);



                    if( op1 instanceof Operator &  (op2 instanceof Proposition | op2 instanceof Absurdity) ) {
                        //upper
                        Operator upper = (Operator) op1;
                        Expression upper_left = upper.getOperand1();
                        Expression upper_right = upper.getOperand2();

                        //Upper left
                        if( upper_left instanceof Operator ){
                            sText(R.id.card_text_2, dots);
                        }
                        else{
                            sText(R.id.card_text_2, upper_left.toString());
                        }

                        //Upper right
                        if( upper_right instanceof Operator ){
                            sText(R.id.card_text_1, dots);
                        }
                        else{
                            sText(R.id.card_text_1, upper_right.toString());
                        }
                        //Upper middle
                        if(upper instanceof Implication){
                            upperImage.setBackgroundResource(R.drawable.horizontal_implication);
                        }
                        else if(upper instanceof Disjunction){
                            upperImage.setBackgroundResource(R.drawable.vertical_disjunction);
                        }
                        else if(upper instanceof Conjunction){
                            upperImage.setBackgroundResource(R.drawable.vertical_conjunction);
                        }



                        //lower
                        rmView(R.id.card_frame_lower);
                        rmView(R.id.card_card_2_4);
                        mParent(R.id.card_card_2_3);
                        sText(R.id.card_text_3,op2.toString());
                    }

                    if( (op1 instanceof Proposition | op1 instanceof Absurdity) & op2 instanceof Operator  ) {

                        //lower
                        Operator lower = (Operator) op2;
                        Expression lower_left = lower.getOperand1();
                        Expression lower_right = lower.getOperand2();

                        //lower left
                        if(lower_left instanceof Operator){
                            sText(R.id.card_text_3,dots);
                        }
                        else{
                            sText(R.id.card_text_3,lower_left.toString());
                        }

                        //lower left
                        if(lower_right instanceof Operator){
                            sText(R.id.card_text_4,dots);
                        }
                        else{
                            sText(R.id.card_text_4,lower_right.toString());
                        }
                        //Lower middle
                        if(lower instanceof Implication){
                            lowerImage.setBackgroundResource(R.drawable.horizontal_implication);
                        }
                        else if(lower instanceof Disjunction){
                            lowerImage.setBackgroundResource(R.drawable.vertical_disjunction);
                        }
                        else if(lower instanceof Conjunction){
                            lowerImage.setBackgroundResource(R.drawable.vertical_conjunction);
                        }



                        //upper
                        rmView(R.id.card_frame_upper);
                        rmView(R.id.card_card_1_1);
                        mParent(R.id.card_card_1_2);
                        sText(R.id.card_text_2,op1.toString());
                    }

                    if( op1 instanceof Operator & op2 instanceof Operator ){
                        //lower and upper
                        Operator lower = (Operator) op2;
                        Expression lower_left = lower.getOperand1();
                        Expression lower_right = lower.getOperand2();

                        //lower left
                        if(lower_left instanceof Operator){
                            sText(R.id.card_text_3,dots);
                        }
                        else{
                            sText(R.id.card_text_3,lower_left.toString());
                        }

                        //lower left
                        if(lower_right instanceof Operator){
                            sText(R.id.card_text_4,dots);
                        }
                        else{
                            sText(R.id.card_text_4,lower_right.toString());
                        }
                        //Lower middle
                        if(lower instanceof Implication){
                            lowerImage.setBackgroundResource(R.drawable.horizontal_implication);
                        }
                        else if(lower instanceof Disjunction){
                            lowerImage.setBackgroundResource(R.drawable.vertical_disjunction);
                        }
                        else if(lower instanceof Conjunction){
                            lowerImage.setBackgroundResource(R.drawable.vertical_conjunction);
                        }




                        Operator upper = (Operator) op1;
                        Expression upper_left = upper.getOperand1();
                        Expression upper_right = upper.getOperand2();

                        //Upper left
                        if( upper_left instanceof Operator ){
                            sText(R.id.card_text_2, dots);
                        }
                        else{
                            sText(R.id.card_text_2, upper_left.toString());
                        }

                        //Upper right
                        if( upper_right instanceof Operator ){
                            sText(R.id.card_text_1, dots);
                        }
                        else{
                            sText(R.id.card_text_1, upper_right.toString());
                        }
                        //Upper middle
                        if(upper instanceof Implication){
                            upperImage.setBackgroundResource(R.drawable.horizontal_implication);
                        }
                        else if(upper instanceof Disjunction){
                            upperImage.setBackgroundResource(R.drawable.vertical_disjunction);
                        }
                        else if(upper instanceof Conjunction){
                            upperImage.setBackgroundResource(R.drawable.vertical_conjunction);
                        }

                    }
                }
            }
        }

        //remove view by id
        private void rmView(int rId){
            View view = topCardView.findViewById(rId);
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.removeView(view);
        }
        //expand to fill by id
        private void mParent(int rId){
            View view = topCardView.findViewById(rId);
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        //sets text in textview by id.
        private void sText(int rId,String s){
            TextView t = topCardView.findViewById(rId);
            t.setText(s);
        }
    }
}

