package com.datx02_18_35.android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by raxxor(Johannes) on 2018-04-03.
 */

public class CardInflator {

    //convertion rate when cards get smaller
    private static final float widthRatio = (float)0.43;
    private static final float heightRatio = (float) 0.41;


    /**
     * Takes a cardview and applies a expression on it
     *
     * @param  cardView  a inflated card_expression layout
     * @param  expr expression to generate
     * @param  width width in DP
     * @param  height height in DP
     * @param  matchParent if used recursively match parent = true will make the card match parents size
     */
    public static void inflate(CardView cardView, Expression expr, final float width, final float height, boolean matchParent) {



        CardView cardView11 = null;
        CardView cardView12 = null;
        CardView cardView21 = null;
        CardView cardView22 = null;
        @SuppressLint("CutPasteId") final CardView quadrentReference1 = cardView.findViewById(R.id.card_expression_card_quadrant1);
        @SuppressLint("CutPasteId") final CardView quadrentReference2 = cardView.findViewById(R.id.card_expression_card_quadrant2);
        @SuppressLint("CutPasteId") final CardView quadrentReference3 = cardView.findViewById(R.id.card_expression_card_quadrant3);
        @SuppressLint("CutPasteId") final CardView quadrentReference4 = cardView.findViewById(R.id.card_expression_card_quadrant4);


        //parameters are given in DP
        if(!matchParent){
            cardView.getLayoutParams().height =  (int) Tools.convertDpToPixel(height);
            cardView.getLayoutParams().width = (int) Tools.convertDpToPixel(width);
            
            // Added here to have numberView always centered of the visual card, do not create new TextViews when creating depth recursively.
            TextView cardNumberView = new TextView(cardView.getContext());
            cardNumberView.setId(R.id.card_number_text_view);
            cardNumberView.setElevation(cardView.getElevation()+2);
            cardNumberView.setVisibility(View.GONE);
            cardNumberView.setTag(R.id.card_number,0);
            cardNumberView.setTextSize(20);
            cardNumberView.setTextColor(Color.BLACK);
            cardNumberView.setGravity(Gravity.CENTER | Gravity.TOP);
            cardNumberView.setBackgroundResource(R.drawable.rounded_textview);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,0);
            cardNumberView.setLayoutParams(params);

            //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)cardNumberView.getLayoutParams();
            //layoutParams.setMargins(4,0,0,0);
            cardView.addView(cardNumberView);

        }

        //whole card one symbol
        if (expr instanceof Proposition | expr instanceof Absurdity) {
            ImageView imageView = cardView.findViewById(R.id.card_expression_quadrant1234);
            sSymbol(expr, imageView);

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
                middleImage.setBackgroundResource(R.drawable.horizontal_implication);
            } else if (op instanceof Disjunction) {
                middleImage.setBackgroundResource(R.drawable.horizontal_disjunction);
            } else if (op instanceof Conjunction) {
                middleImage.setBackgroundResource(R.drawable.horizontal_conjunction);
            }


            //no complex on up/down card.
            if ((exp1 instanceof Proposition | exp1 instanceof Absurdity) & (exp2 instanceof Proposition | exp2 instanceof Absurdity)) {
                ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                imageViewUpper.setRotation(90);
                ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                imageViewLower.setRotation(90);
                sSymbol(exp1, imageViewUpper);
                sSymbol(exp2, imageViewLower);

                //clean-up
                rmView(R.id.card_expression_card_quadrant1,cardView);
                rmView(R.id.card_expression_card_quadrant2,cardView);
                rmView(R.id.card_expression_card_quadrant3,cardView);
                rmView(R.id.card_expression_card_quadrant4,cardView);

            } else {


                if (exp1 instanceof Proposition | exp1 instanceof Absurdity) {
                    //Upper
                    ImageView imageViewUpper = cardView.findViewById(R.id.card_expression_quadrant12);
                    imageViewUpper.setRotation(90);
                    sSymbol(exp1, imageViewUpper);

                    //clean-up
                    rmView(R.id.card_expression_card_quadrant1,cardView);
                    rmView(R.id.card_expression_card_quadrant2,cardView);
                    rmView(R.id.card_expression_top_mid,cardView);
                }

                if (exp2 instanceof Proposition | exp2 instanceof Absurdity) {
                    //Lower
                    ImageView imageViewLower = cardView.findViewById(R.id.card_expression_quadrant34);
                    imageViewLower.setRotation(90);
                    sSymbol(exp2, imageViewLower);

                    //clean-up
                    rmView(R.id.card_expression_card_quadrant3,cardView);
                    rmView(R.id.card_expression_card_quadrant4,cardView);
                    rmView(R.id.card_expression_lower_mid,cardView);
                }





                if (exp1 instanceof Operator) {

                    Operator op1 = (Operator) exp1;
                    Expression op12 = op1.getOperand1();
                    Expression op11 = op1.getOperand2();

                    //Upper
                    ImageView imageViewUpperLeft = cardView.findViewById(R.id.card_expression_quadrant2);
                    ImageView imageViewUpperRight = cardView.findViewById(R.id.card_expression_quadrant1);
                    ImageView imageUpperMiddle = cardView.findViewById(R.id.card_expression_top_mid);

                    if(op11 instanceof Proposition | op11 instanceof Absurdity){
                        sSymbol(op11, imageViewUpperRight);
                    }
                    if(op12 instanceof Proposition | op12 instanceof Absurdity){
                        sSymbol(op12,imageViewUpperLeft);
                    }
                    if (op1 instanceof Implication) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.vertical_implication);
                    } else if (op1 instanceof Disjunction) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.vertical_disjunction);
                    } else if (op1 instanceof Conjunction) {
                        imageUpperMiddle.setBackgroundResource(R.drawable.vertical_conjunction);
                    }
                    CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant1));
                    cardView11 = newSmallCard(cardView,cardViewQuad,op11);
                    cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant2));
                    cardView12 = newSmallCard(cardView,cardViewQuad,op12);
                }


                if (exp2 instanceof Operator) {

                    Operator op2 = (Operator) exp2;
                    Expression op21 = op2.getOperand1();
                    Expression op22 = op2.getOperand2();

                    //Upper
                    ImageView imageViewLowerLeft = cardView.findViewById(R.id.card_expression_quadrant3);
                    ImageView imageViewLowerRight = cardView.findViewById(R.id.card_expression_quadrant4);
                    ImageView imageLowerMiddle = cardView.findViewById(R.id.card_expression_lower_mid);


                    if(op21 instanceof Proposition | op21 instanceof Absurdity){
                        sSymbol(op21,imageViewLowerLeft);
                    }
                    if(op22 instanceof Proposition | op22 instanceof Absurdity){
                        sSymbol(op22,imageViewLowerRight);
                    }

                    if(op2 instanceof Implication){
                        imageLowerMiddle.setBackgroundResource(R.drawable.vertical_implication);
                    }
                    else if(op2 instanceof Disjunction){
                        imageLowerMiddle.setBackgroundResource(R.drawable.vertical_disjunction);
                    }
                    else if(op2 instanceof Conjunction){
                        imageLowerMiddle.setBackgroundResource(R.drawable.vertical_conjunction);
                    }
                    CardView cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant3));
                    cardView21 = newSmallCard(cardView,cardViewQuad,op21);
                    cardViewQuad =  ((CardView)cardView.findViewById(R.id.card_expression_card_quadrant4));
                    cardView22 = newSmallCard(cardView,cardViewQuad,op22);
                }

                if(cardView11 !=null){
                    quadrentReference1.addView(cardView11);
                }
                if(cardView12 !=null){
                    quadrentReference2.addView(cardView12);
                }
                if(cardView21 !=null){
                    quadrentReference3.addView(cardView21);
                }
                if(cardView22 !=null){
                    quadrentReference4.addView(cardView22);
                }
            }
        }
    }

    //new card recursion
    private static CardView newSmallCard(CardView topCardView,CardView cardViewQuad, Expression expression ){
        cardViewQuad.setCardBackgroundColor(Color.TRANSPARENT);
        cardViewQuad.setCardElevation(0);
        CardView smallCardView = (CardView) LayoutInflater.from(topCardView.getContext()).inflate(R.layout.card_expression,cardViewQuad , false);
        smallCardView.setCardBackgroundColor(Color.TRANSPARENT);
        smallCardView.setCardElevation(0);
        ViewGroup.MarginLayoutParams layoutParams4 =
                (ViewGroup.MarginLayoutParams) smallCardView.getLayoutParams();
        layoutParams4.setMargins(0, 0, 0, 0);
        inflate(smallCardView,expression,0,0,true);
        return smallCardView;
    }

    //remove view by id
    private static void rmView(int rId,CardView card){
        View view = card.findViewById(rId);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
    }
    //set symbol by expression
    private static void sSymbol(Expression expression,ImageView imageView){
        if (expression instanceof Absurdity) {
            Tools.setImage(imageView, R.drawable.absurdity);
            return;
        }
        else if (false == (expression instanceof Proposition)) {
            throw new IllegalArgumentException("Expression must either be a proposition or absurdity");
        }

        String symbol = ((Proposition)expression).getSymbol();
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
            case "arrows":
                Tools.setImage(imageView,R.drawable.arrows);
                break;
            case "cross":
                Tools.setImage(imageView,R.drawable.cross);
                break;
            case "crossandcircle":
                Tools.setImage(imageView,R.drawable.cross_and_circle);
                break;
            case "diamond":
                Tools.setImage(imageView,R.drawable.diamond);
                break;
            case "donut":
                Tools.setImage(imageView,R.drawable.donut);
                break;
            case "squares":
                Tools.setImage(imageView,R.drawable.squares);
                break;
            case "sun":
                Tools.setImage(imageView,R.drawable.sun);
                break;
            case "wonky":
                Tools.setImage(imageView,R.drawable.wonky);
                break;
            default:
                Tools.setImage(imageView,R.drawable.dots);
                break;
        }
    }
}