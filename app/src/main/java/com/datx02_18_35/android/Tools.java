package com.datx02_18_35.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
 * Created by raxxor on 2018-03-15.
 */

class   Tools {

    static final String debug = "test123";
    private static final String userData = "userDatas";


    //save userdata
    public static void writeUserData(byte[] array, Context context){
        String toSave =  Base64.encodeToString(array, Base64.DEFAULT);
        SharedPreferences preferences = context.getSharedPreferences(userData,Context.MODE_PRIVATE);
        preferences.edit().putString(userData,toSave).apply();
    }


    //get userdata
    public static byte[] getUserData(Context context){
        SharedPreferences preferences = context.getSharedPreferences(userData,Context.MODE_PRIVATE);

        String string = preferences.getString(userData,null);
        if(string != null){
            return Base64.decode(string, Base64.DEFAULT);
        }
        return null;

    }
    public static void deleteAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.delete);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void selectAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.select);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void deselectAnimation(Context ctx, View v){
        Animation a =AnimationUtils.loadAnimation(ctx, R.anim.deselect);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_left(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_right(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }



    //screen
    static float getWidthDp(Context context){
        float px = Resources.getSystem().getDisplayMetrics().widthPixels;
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    //"generate card"

    static class CardDeflator{
        final CardView topCardView;
        final String dots = " .. ";


        CardDeflator(CardView cardView, Expression expr, Map<String,String> symbolMap){
            topCardView = cardView;

            //whole card one symbol
            if(expr instanceof Proposition | expr instanceof Absurdity){
                topCardView.removeAllViews();

                CardView card =  new CardView(topCardView.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(5,5,5,5);
                card.setLayoutParams(params);
                ImageView imageView = new ImageView(topCardView.getContext());
                card.addView(imageView);
                topCardView.addView(card);
                sSymbol(expr,imageView,symbolMap);
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
                    rmView(R.id.card_frame_lower,topCardView);
                    rmView(R.id.card_frame_upper,topCardView);
                    rmView(R.id.card_card_1_1,topCardView);
                    rmView(R.id.card_card_2_4,topCardView);

                    mParent(R.id.card_card_1_2,topCardView);
                    mParent(R.id.card_card_2_3,topCardView);

                    if(op1 instanceof Proposition) {
                        sSymbol((Proposition) op1, topCardView, R.id.card_image_2, symbolMap);
                    }else {
                        sSymbol((Absurdity) op1,topCardView,R.id.card_image_2,symbolMap);
                    }
                    if(op2 instanceof Proposition) {
                        sSymbol((Proposition) op2, topCardView, R.id.card_image_3, symbolMap);
                    }else {
                        sSymbol((Absurdity) op2, topCardView, R.id.card_image_3, symbolMap);
                    }
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
                            sDotsSymbol(topCardView,R.id.card_image_2);
                        }
                        else{
                            sSymbol( upper_left,topCardView,R.id.card_image_2,symbolMap);
                        }

                        //Upper right
                        if( upper_right instanceof Operator ){
                            sDotsSymbol(topCardView,R.id.card_image_1);
                        }
                        else{
                            sSymbol( upper_right,topCardView,R.id.card_image_1,symbolMap);
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
                        rmView(R.id.card_frame_lower,topCardView);
                        rmView(R.id.card_card_2_4,topCardView);
                        mParent(R.id.card_card_2_3,topCardView);

                        sSymbol(op2,topCardView,R.id.card_image_3,symbolMap);
                    }

                    if( (op1 instanceof Proposition | op1 instanceof Absurdity) & op2 instanceof Operator  ) {

                        //lower
                        Operator lower = (Operator) op2;
                        Expression lower_left = lower.getOperand1();
                        Expression lower_right = lower.getOperand2();

                        //lower left
                        if(lower_left instanceof Operator){
                            sDotsSymbol(topCardView,R.id.card_image_3);
                        }
                        else{
                            sSymbol(lower_left,topCardView,R.id.card_image_3,symbolMap);
                        }

                        //lower left
                        if(lower_right instanceof Operator){
                            sDotsSymbol(topCardView,R.id.card_image_4);
                        }
                        else{
                            sSymbol( lower_right,topCardView,R.id.card_image_4,symbolMap);
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
                        rmView(R.id.card_frame_upper,topCardView);
                        rmView(R.id.card_card_1_1,topCardView);
                        mParent(R.id.card_card_1_2,topCardView);
                        sSymbol(op1,topCardView,R.id.card_image_2,symbolMap);
                    }

                    if( op1 instanceof Operator & op2 instanceof Operator ){
                        //lower and upper
                        Operator lower = (Operator) op2;
                        Expression lower_left = lower.getOperand1();
                        Expression lower_right = lower.getOperand2();

                        //lower left
                        if(lower_left instanceof Operator){
                            sDotsSymbol(topCardView,R.id.card_image_3);
                        }
                        else{
                            sSymbol( lower_left,topCardView,R.id.card_image_3,symbolMap);
                        }

                        //lower left
                        if(lower_right instanceof Operator){
                            sDotsSymbol(topCardView,R.id.card_image_4);
                        }
                        else{
                            sSymbol(lower_right,topCardView,R.id.card_image_4,symbolMap);
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
                            sDotsSymbol(topCardView,R.id.card_image_2);
                        }
                        else{
                            sSymbol( upper_left,topCardView,R.id.card_image_2,symbolMap);
                        }

                        //Upper right
                        if( upper_right instanceof Operator ){
                            sDotsSymbol(topCardView,R.id.card_image_1);
                        }
                        else{
                            sSymbol( upper_right,topCardView,R.id.card_image_1,symbolMap);
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
            String symbol = "";
            if(symbolMap.containsKey(expression.toString())){
                symbol = symbolMap.get(expression.toString());
            }

            switch (symbol.toLowerCase()){
                case "redball":
                    setImage(imageView,R.drawable.redball);
                    break;
                case "blueball" :
                    setImage(imageView,R.drawable.blueball);
                    break;
                    default:
                        setImage(imageView,R.drawable.dots);
                        break;

            }
        }
        private static void sSymbol(Expression expression,ImageView imageView, Map<String,String> symbolMap){
            String symbol = "";
            if(symbolMap.containsKey(expression.toString())){
                symbol = symbolMap.get(expression.toString());
            }

            switch (symbol.toLowerCase()){
                case "redball":
                    setImage(imageView,R.drawable.redball);
                    break;
                case "blueball" :
                    setImage(imageView,R.drawable.blueball);
                    break;
                default:
                    setImage(imageView,R.drawable.dots);
                    break;

            }
        }

        private static void sDotsSymbol(CardView cardView,int rId){
            ( (ImageView) cardView.findViewById(rId) ).setImageResource(R.drawable.dots);
        }
    }
    public static void setImage(ImageView image, int rId){
        int imageHeight = 100;
        int imageWidth = 100;
        image.setImageBitmap(decodeSampledBitmapFromResource(image.getResources(), rId, imageWidth, imageHeight));

    }


    //imported code from the official Android documentation
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    //imported code from the official Android documentation
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
