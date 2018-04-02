package com.datx02_18_35.android;

import android.content.Context;
import android.support.v7.widget.CardView;
import com.datx02_18_35.model.expression.Expression;
import java.util.Map;

public class CardFactory {
    CardView topCardView;


    private static CardFactory singleton = null;
    public static CardFactory getSingleton() {
        if(singleton==null){
            singleton=new CardFactory();
        }
        return singleton;
    }

    public CardView generateCard(Context context, Expression expr, Map<String,String> symbolMap, int pWidth, int dpHeight){
        topCardView = new CardView(context);
        return null;
    }
}
