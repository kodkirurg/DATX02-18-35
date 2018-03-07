package com.datx02_18_35.android;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

public class FragmentBoardCards extends Fragment implements OnStartDragListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_cards,
                container, false);


        int spanCount;
        int widthDP=Math.round(getWidthDp(getActivity().getApplicationContext())) - 130*2;
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);
        recyclerView = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<Expression> list = new ArrayList<Expression>();
        ExpressionFactory exprFact = ExpressionFactory.getSingleton();
        Expression p1 = exprFact.createProposition("P");
        Expression q1 = exprFact.createProposition("Q");
        Expression r1 = exprFact.createProposition("R");
        Expression c4 = exprFact.createOperator(OperatorType.IMPLICATION, p1,q1);
        Expression c5 = exprFact.createOperator(OperatorType.DISJUNCTION, r1,q1);
        Expression abs = exprFact.createAbsurdity();

        Expression complex = exprFact.createOperator(OperatorType.IMPLICATION, c4,c5);
        Expression upperC_lowerS = exprFact.createOperator(OperatorType.IMPLICATION, c4,p1);
        Expression upperS_lowerC = exprFact.createOperator(OperatorType.IMPLICATION, p1,c4);
        Expression upperC_lowerC = exprFact.createOperator(OperatorType.IMPLICATION, c4,c5);
        Expression upperCC_lowerCC = exprFact.createOperator(OperatorType.IMPLICATION, upperC_lowerC ,upperC_lowerC );

        list.add(upperC_lowerS);
        list.add(upperC_lowerC);
        list.add(upperCC_lowerCC);

        recAdapter = new RecyclerAdapter(list);


        //add drag and drop
        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback((RecyclerAdapter) recAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);

        return view;
    }



    public static float getWidthDp(Context context){
        float px = Resources.getSystem().getDisplayMetrics().widthPixels;
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

}
