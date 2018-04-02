package com.datx02_18_35.android;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.datx02_18_35.model.expression.Absurdity;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Proposition;

import java.util.Map;

import game.logic_game.R;

public class CardFactory {

    private static final int cardElevation = 20; //dp units
    private static final int cardMargins = Math.round(Tools.convertDpToPixel((float)5));

    /*
    OnCreate generates this template which will later be changed by onbindviewholder
     */
    static CardView generateTemplate(Context context, int dpWidth, int dpHeight){
        CardView topCardView = new CardView(context);

        //convert values
        int pxWidth = Math.round(Tools.convertDpToPixel(dpWidth));
        int pxHeight = Math.round(Tools.convertDpToPixel(dpHeight));

        //layout param set values
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pxWidth,pxHeight);
        int dpMargin = cardMargins;
        lp.setMargins(dpMargin,dpMargin,dpMargin,dpMargin);
        topCardView.setCardElevation(cardElevation);

        topCardView.setLayoutParams(lp);
        return topCardView;
    }


    //OnBindviewholder uses this to generate the correct card from template
    static void paintExpressionOnTemplate(CardView cardView, Expression expr, Map<String,String> symbolMap){

        //leaf
        if(expr instanceof Proposition | expr instanceof Absurdity){
            CardView card =  new CardView(cardView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            card.setLayoutParams(params);
            ImageView imageView = new ImageView(cardView.getContext());
            card.addView(imageView);
            cardView.addView(card);
            sSymbol(expr,imageView,symbolMap);
        }

    }





    /*
    Helper functions for views
     */

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
                Tools.setImage(imageView, R.drawable.redball);
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
