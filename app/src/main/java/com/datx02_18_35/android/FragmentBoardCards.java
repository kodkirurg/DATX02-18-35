package com.datx02_18_35.android;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;


import java.util.ArrayList;

import game.logic_game.R;

public class FragmentBoardCards extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ArrayList<Expression> list = new ArrayList<Expression>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_cards,
                container, false);

        //"screen" re-size
        int spanCount;
        int widthDP=Math.round(getWidthDp(getActivity().getApplicationContext())) - 130*2;
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);


        recyclerView = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(recLayoutManager);


        recAdapter = new GameCardAdapter(list);

        recyclerView.setAdapter(recAdapter);

        ((Game)getActivity()).ready.release(1);
        return view;
    }



    public static float getWidthDp(Context context){
        float px = Resources.getSystem().getDisplayMetrics().widthPixels;
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }



    public void updateBoard(final Iterable<Expression> iterable){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int size =list.size();
                list.clear();
                recAdapter.notifyItemRangeRemoved(0,size);
                for (Expression anIterable : iterable) {
                        list.add(anIterable);
                }
                recAdapter.notifyItemRangeInserted(0,list.size());
            }
        });
        ((GameCardAdapter) recAdapter).resetSelected();
        recyclerView.getRecycledViewPool().clear();
    }

}
