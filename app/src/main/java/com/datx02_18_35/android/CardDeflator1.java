package com.datx02_18_35.android;

import android.content.Context;
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
import java.util.zip.Inflater;

import game.logic_game.R;

/**
 * Created by raxxor(Johannes) on 2018-04-03.
 */

public class CardDeflator1 {

    private static final String dots = " .. ";
    private static final double widthRatio = 0.43;
    private static final double heightRatio = 0.41;


    public static void deflate(final CardView cardView, Expression expr, final Map<String,String> symbolMap, final double width, final double height, boolean matchParent) {



        if(!matchParent){
            cardView.getLayoutParams().height = (int) height;
            cardView.getLayoutParams().width = (int) width;

        }

        //whole card one symbol
        if (expr instanceof Proposition | expr instanceof Absurdity) {
            ImageView imageView = cardView.findViewById(R.id.card_expression_quadrant1234);
            sSymbol(expr, imageView, symbolMap);
        } else {
            Operator op = (Operator) expr;
            Expression exp1 = op.getOperand1();
            Expression exp2 = op.getOperand2();

            ImageView middleImage = cardView.findViewById(R.id.card_expression_mid_mid);
            middleImage.setVisibility(View.VISIBLE);
            if (op instanceof Implication) {
                middleImage.setBackgroundResource(R.drawable.vertical_implication);
            } else if (op instanceof Disjunction) {
                middleImage.setBackgroundResource(R.drawable.horizontal_disjunction);
            } else if (op instanceof Conjunction) {
                middleImage.setBackgroundResource(R.drawable.horizontal_conjunction);
            }


            //no complex on up/down card.
            if ((exp1 instanceof Proposition | exp1 instanceof Absurdity) & (exp2 instanceof Proposition | exp2 instanceof Absurdity)) {
                ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                sSymbol(exp1, imageViewUpper, symbolMap);
                sSymbol(exp2, imageViewLower, symbolMap);
            } else {

                if (exp1 instanceof Operator) {

                    Operator op1 = (Operator) exp1;
                    Expression op11 = op1.getOperand1();
                    Expression op12 = op1.getOperand2();

                    //Upper
                    ImageView imageViewUpperLeft = cardView.findViewById(R.id.card_expression_quadrant2);
                    ImageView imageViewUpperRight = cardView.findViewById(R.id.card_expression_quadrant1);
                    ImageView imageUpperMiddle = cardView.findViewById(R.id.card_expression_top_mid);

                    sSymbol(op11, imageViewUpperLeft, symbolMap);
                    sSymbol(op12, imageViewUpperRight, symbolMap);
                    if (op1 instanceof Implication) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.horizontal_implication);
                    } else if (op1 instanceof Disjunction) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.vertical_disjunction);
                    } else if (op1 instanceof Conjunction) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.vertical_conjunction);
                    }
                    imageUpperMiddle.setVisibility(View.VISIBLE);


                    if(op11 instanceof Operator & !matchParent){
                        CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant1));
                        rmView(R.id.card_expression_quadrant1,cardView);
                        CardView cardView1 = (CardView) LayoutInflater.from(cardView.getContext()).inflate(R.layout.card_expression1,cardViewQuad , false);
                        ViewGroup.MarginLayoutParams layoutParams =
                                (ViewGroup.MarginLayoutParams) cardView1.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        cardView1.requestLayout();
                        deflate(cardView1,op11,symbolMap,width*widthRatio,height*heightRatio,true);
                        ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant1)).addView(cardView1);
                        Log.d(Tools.debug, "deflate: " + op11.toString());

                    }



                }


                if (exp2 instanceof Operator) {

                    Operator op2 = (Operator) exp2;
                    Expression op21 = op2.getOperand1();
                    Expression op22 = op2.getOperand2();

                    //Upper
                    ImageView imageViewLowerLeft = cardView.findViewById(R.id.card_expression_quadrant3);
                    ImageView imageViewLowerRight = cardView.findViewById(R.id.card_expression_quadrant4);
                    ImageView imageLowerMiddle = cardView.findViewById(R.id.card_expression_lower_mid);

                    sSymbol(op21,imageViewLowerLeft,symbolMap);
                    sSymbol(op22,imageViewLowerRight,symbolMap);

                    if(op2 instanceof Implication){
                        imageLowerMiddle.setBackgroundResource(R.drawable.horizontal_implication);
                    }
                    else if(op2 instanceof Disjunction){
                        imageLowerMiddle.setBackgroundResource(R.drawable.vertical_disjunction);
                    }
                    else if(op2 instanceof Conjunction){
                        imageLowerMiddle.setBackgroundResource(R.drawable.vertical_conjunction);
                    }
                    imageLowerMiddle.setVisibility(View.VISIBLE);
                }

                if (exp1 instanceof Proposition | exp1 instanceof Absurdity) {
                    //Upper
                    ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                    sSymbol(exp1, imageViewUpper, symbolMap);
                }

                if (exp2 instanceof Proposition | exp2 instanceof Absurdity) {
                    //Lower
                    ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                    sSymbol(exp2, imageViewLower, symbolMap);
                }




/*
                final CardView item = cardView.findViewById(R.id.card_card_2_3);
                final CardView smallCardView = (CardView) LayoutInflater.from(item.getContext()).inflate(R.layout.card_expression, item, false);


                double newWidthSmallerCard = widthSmallerCard * widthRatio;
                double newHeightSmallerCard = heightSmallerCard * heightRatio;

                */

                //CardDeflator1.deflate(smallCardView,lower_left,symbolMap,newWidthSmallerCard,newHeightSmallerCard);


                //sDotsSymbol(cardView,R.id.card_image_3);


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
        View view = (View) imageView.getParent();
        view.setVisibility(View.VISIBLE);
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