package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datx02_18_35.model.expression.Absurdity;
import com.datx02_18_35.model.expression.Conjunction;
import com.datx02_18_35.model.expression.Disjunction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Implication;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.Proposition;

import java.util.Map;

import game.logic_game.R;

/**
 * Created by robin on 2018-03-27.
 */

public class CardDeflator1 {
    private CardDeflator1(){};

    private static final String dots = " .. ";
    private static final double widthRatio = 0.43;
    private static final double heightRatio = 0.41;


    public static void deflate(final CardView cardView, Expression expr, final Map<String,String> symbolMap, final double widthSmallerCard, final double heightSmallerCard){


        cardView.getLayoutParams().height=(int)heightSmallerCard;
        cardView.getLayoutParams().width=(int)widthSmallerCard;

        //whole card one symbol
        if(expr instanceof Proposition | expr instanceof Absurdity){
            ImageView imageView = cardView.findViewById(R.id.card_expression_quadrant1234);
            sSymbol(expr,imageView,symbolMap);
        }

        else {
            Operator op = (Operator) expr;
            Expression op1 = op.getOperand1();
            Expression op2 = op.getOperand2();

            ImageView middleImage = cardView.findViewById(R.id.card_expression_mid_mid);
            middleImage.setVisibility(View.VISIBLE);
            if(op instanceof Implication){
                middleImage.setBackgroundResource(R.drawable.vertical_implication);
            }
            else if(op instanceof Disjunction){
                middleImage.setBackgroundResource(R.drawable.horizontal_disjunction);
            }
            else if(op instanceof Conjunction){
                middleImage.setBackgroundResource(R.drawable.horizontal_conjunction);
            }


            //no complex on up/down card.
            if ((op1 instanceof Proposition | op1 instanceof Absurdity) &  (op2 instanceof Proposition | op2 instanceof Absurdity) ){
                ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                sSymbol(op1,imageViewUpper,symbolMap);
                sSymbol(op2,imageViewLower,symbolMap);


            }
            else{
                ImageView upperImage = cardView.findViewById(R.id.card_image_upper);
                ImageView lowerImage = cardView.findViewById(R.id.card_image_lower);
                cardView.findViewById(R.id.card_frame_lower).setBackgroundColor(Color.WHITE);
                cardView.findViewById(R.id.card_frame_upper).setBackgroundColor(Color.WHITE);



                if( op1 instanceof Operator &  (op2 instanceof Proposition | op2 instanceof Absurdity) ) {
                    //upper
                    Operator upper = (Operator) op1;
                    Expression upper_left = upper.getOperand1();
                    Expression upper_right = upper.getOperand2();

                    //Upper left
                    if( upper_left instanceof Operator ){
                        sDotsSymbol(cardView,R.id.card_image_2);
                    }
                    else{
                        sSymbol( upper_left,cardView,R.id.card_image_2,symbolMap);
                    }

                    //Upper right
                    if( upper_right instanceof Operator ){
                        sDotsSymbol(cardView,R.id.card_image_1);
                    }
                    else{
                        sSymbol( upper_right,cardView,R.id.card_image_1,symbolMap);
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
                    rmView(R.id.card_frame_lower,cardView);
                    rmView(R.id.card_card_2_4,cardView);
                    mParent(R.id.card_card_2_3,cardView);

                    sSymbol(op2,cardView,R.id.card_image_3,symbolMap);
                }

                if( (op1 instanceof Proposition | op1 instanceof Absurdity) & op2 instanceof Operator  ) {

                    //lower
                    Operator lower = (Operator) op2;
                    Expression lower_left = lower.getOperand1();
                    Expression lower_right = lower.getOperand2();

                    //lower left
                    if(lower_left instanceof Operator){
                        sDotsSymbol(cardView,R.id.card_image_3);
                    }
                    else{
                        sSymbol(lower_left,cardView,R.id.card_image_3,symbolMap);
                    }

                    //lower left
                    if(lower_right instanceof Operator){
                        sDotsSymbol(cardView,R.id.card_image_4);
                    }
                    else{
                        sSymbol( lower_right,cardView,R.id.card_image_4,symbolMap);
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
                    rmView(R.id.card_frame_upper,cardView);
                    rmView(R.id.card_card_1_1,cardView);
                    mParent(R.id.card_card_1_2,cardView);
                    sSymbol(op1,cardView,R.id.card_image_2,symbolMap);
                }

                if( op1 instanceof Operator & op2 instanceof Operator ){
                    //lower and upper
                    Operator lower = (Operator) op2;
                    final Expression lower_left = lower.getOperand1();
                    Expression lower_right = lower.getOperand2();

                    //lower left
                    if(lower_left instanceof Operator){





                        final CardView item = cardView.findViewById(R.id.card_card_2_3);
                        final CardView smallCardView = (CardView) LayoutInflater.from(item.getContext()).inflate(R.layout.card_expression, item,false);


                        double newWidthSmallerCard = widthSmallerCard * widthRatio;
                        double newHeightSmallerCard = heightSmallerCard * heightRatio;

                        CardDeflator1.deflate(smallCardView,lower_left,symbolMap,newWidthSmallerCard,newHeightSmallerCard);



                        //sDotsSymbol(cardView,R.id.card_image_3);






                    }
                    else{
                        sSymbol( lower_left,cardView,R.id.card_image_3,symbolMap);
                    }

                    //lower left
                    if(lower_right instanceof Operator){
                        sDotsSymbol(cardView,R.id.card_image_4);
                    }
                    else{
                        sSymbol(lower_right,cardView,R.id.card_image_4,symbolMap);
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
                        sDotsSymbol(cardView,R.id.card_image_2);
                    }
                    else{
                        sSymbol( upper_left,cardView,R.id.card_image_2,symbolMap);
                    }

                    //Upper right
                    if( upper_right instanceof Operator ){
                        sDotsSymbol(cardView,R.id.card_image_1);
                    }
                    else{
                        sSymbol( upper_right,cardView,R.id.card_image_1,symbolMap);
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
        /*
        TextView cardNumberView = new TextView(cardView.getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cardNumberView.setLayoutParams(lp);
        cardNumberView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        cardNumberView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25f);
        cardNumberView.setId(R.id.card_number_text_view);
        cardNumberView.setElevation(cardView.getElevation()+1);
        cardNumberView.setVisibility(View.GONE);
        cardView.addView(cardNumberView);
        */
    }

    //remove view by id
    private static void rmView(int rId,CardView card){
        View view = card.findViewById(rId);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
    }
    //expand to fill by id
    private static void mParent(int rId,CardView card){
        View view = card.findViewById(rId);
        view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }
    //sets text in textview by id.
    private static void sSymbol(Expression expression,CardView cardView,int rId, Map<String,String> symbolMap){
        ImageView imageView = cardView.findViewById(rId);
        sSymbol(expression, imageView, symbolMap);
    }
    private static void sSymbol(Expression expression,ImageView imageView, Map<String,String> symbolMap){
        String symbol = "";
        //assume standard is invisible
        imageView.setVisibility(View.VISIBLE);
        if(symbolMap.containsKey(expression.toString())){
            symbol = symbolMap.get(expression.toString());
        }

        switch (symbol.toLowerCase()){
            case "redball":
                Tools.setImage(imageView,R.drawable.redball);
                break;
            case "blueball" :
                Tools.setImage(imageView,R.drawable.blueball);
                break;
            case "greentriangle" :
                Tools.setImage(imageView,R.drawable.greentriangle);
                break;
            case "yellowrectangle":
                Tools.setImage(imageView,R.drawable.yellowrectangle);
                break;
            case "absurdity":
                Tools.setImage(imageView,R.drawable.absurdity);
                break;
            default:
                Tools.setImage(imageView,R.drawable.dots);
                break;

        }
    }

    private static void sDotsSymbol(CardView cardView,int rId){
        ( (ImageView) cardView.findViewById(rId) ).setImageResource(R.drawable.dots);
    }
}