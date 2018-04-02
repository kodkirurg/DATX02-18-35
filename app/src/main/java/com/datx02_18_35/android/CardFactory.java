package com.datx02_18_35.android;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;

import com.datx02_18_35.model.expression.Expression;
import java.util.Map;

public class CardFactory {

    private static final int cardElevation = 20; //dp units
    private static final int cardMargins = Math.round(Tools.convertDpToPixel((float)5));


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

    static void paintExpressionOnTemplate(Context context, Expression expr, Map<String,String> symbolMap, int dpWidth, int dpHeight){

    }
}
