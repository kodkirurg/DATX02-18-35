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


    public static void deflate(CardView cardView, Expression expr, final Map<String,String> symbolMap, final double width, final double height, boolean matchParent) {



        CardView cardView11 = null;
        CardView cardView12 = null;
        CardView cardView21 = null;
        CardView cardView22 = null;

        if(!matchParent){
            cardView.getLayoutParams().height = (int) height;
            cardView.getLayoutParams().width = (int) width;

        }

        //whole card one symbol
        if (expr instanceof Proposition | expr instanceof Absurdity) {
            ImageView imageView = cardView.findViewById(R.id.card_expression_quadrant1234);
            sSymbol(expr, imageView, symbolMap);

            //clean-up
            rmView(R.id.card_expression_card_quadrant1,cardView);
            rmView(R.id.card_expression_card_quadrant2,cardView);
            rmView(R.id.card_expression_card_quadrant3,cardView);
            rmView(R.id.card_expression_card_quadrant4,cardView);

        } else {
            Operator op = (Operator) expr;
            Expression exp1 = op.getOperand1();
            Expression exp2 = op.getOperand2();

            ImageView middleImage = cardView.findViewById(R.id.card_expression_mid_mid);
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

                //clean-up
                rmView(R.id.card_expression_card_quadrant1,cardView);
                rmView(R.id.card_expression_card_quadrant2,cardView);
                rmView(R.id.card_expression_card_quadrant3,cardView);
                rmView(R.id.card_expression_card_quadrant4,cardView);

            } else {


                if (exp1 instanceof Proposition | exp1 instanceof Absurdity) {
                    //Upper
                    ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                    sSymbol(exp1, imageViewUpper, symbolMap);

                    //clean-up
                    rmView(R.id.card_expression_card_quadrant1,cardView);
                    rmView(R.id.card_expression_card_quadrant2,cardView);
                    rmView(R.id.card_expression_top_mid,cardView);
                }

                if (exp2 instanceof Proposition | exp2 instanceof Absurdity) {
                    //Lower
                    ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                    sSymbol(exp2, imageViewLower, symbolMap);

                    //clean-up
                    rmView(R.id.card_expression_card_quadrant3,cardView);
                    rmView(R.id.card_expression_card_quadrant4,cardView);
                    rmView(R.id.card_expression_lower_mid,cardView);
                }





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



                    if(op11 instanceof Operator){
                        CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant1));
                        rmView(R.id.card_expression_quadrant1,cardView);
                        cardView11 = (CardView) LayoutInflater.from(cardView.getContext()).inflate(R.layout.card_expression1,cardViewQuad , false);
                        ViewGroup.MarginLayoutParams layoutParams =
                                (ViewGroup.MarginLayoutParams) cardView11.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        deflate(cardView11,op11,symbolMap,width*widthRatio,height*heightRatio,true);
                        Log.d(Tools.debug, "deflate: " + op11.toString());

                    }
                    if(op12 instanceof Operator){
                        CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant2));
                        rmView(R.id.card_expression_quadrant2,cardView);
                        cardView12 = (CardView) LayoutInflater.from(cardView.getContext()).inflate(R.layout.card_expression1,cardViewQuad , false);
                        ViewGroup.MarginLayoutParams layoutParams =
                                (ViewGroup.MarginLayoutParams) cardView12.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        deflate(cardView12,op12,symbolMap,width*widthRatio,height*heightRatio,true);
                        Log.d(Tools.debug, "deflate: " + op12.toString());
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

                    if(op21 instanceof Operator){
                        CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant3));
                        rmView(R.id.card_expression_quadrant3,cardView);
                        cardView21 = (CardView) LayoutInflater.from(cardView.getContext()).inflate(R.layout.card_expression1,cardViewQuad , false);
                        ViewGroup.MarginLayoutParams layoutParams =
                                (ViewGroup.MarginLayoutParams) cardView21.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        deflate(cardView21,op21,symbolMap,width*widthRatio,height*heightRatio,true);
                        Log.d(Tools.debug, "deflate: " + op21.toString());

                    }
                    if(op22 instanceof Operator){
                        CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant4));
                        rmView(R.id.card_expression_quadrant4,cardView);
                        cardView22 = (CardView) LayoutInflater.from(cardView.getContext()).inflate(R.layout.card_expression1,cardViewQuad , false);
                        ViewGroup.MarginLayoutParams layoutParams =
                                (ViewGroup.MarginLayoutParams) cardView22.getLayoutParams();
                        layoutParams.setMargins(0, 0, 0, 0);
                        deflate(cardView22,op22,symbolMap,width*widthRatio,height*heightRatio,true);
                        Log.d(Tools.debug, "deflate: " + op22.toString());
                    }





                }

                if(cardView11 !=null){
                    ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant1)).addView(cardView11);
                }
                if(cardView12 !=null){
                    ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant2)).addView(cardView12);
                }
                if(cardView21 !=null){
                    ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant3)).addView(cardView21);
                }
                if(cardView22 !=null){
                    ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant4)).addView(cardView22);
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