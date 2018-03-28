package com.datx02_18_35.android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;

import game.logic_game.R;


/**
 * Created by Magnu on 2018-03-13.
 */

public class FragmentSandboxCards extends Fragment {

    public RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ArrayList<Expression> list = new ArrayList<Expression>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandbox_cards,
                container, false);

        //"screen" re-size
        int spanCount;
        int widthDP=Math.round(Tools.getWidthDp(getActivity().getApplicationContext())) - 130*2;
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);

        recyclerView = (RecyclerView) view.findViewById(R.id.sandboxLeft_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(recLayoutManager);


        list.addAll(GameBoard.level.usedSymbols);
        recAdapter = new SandboxCardsAdapter(list);

        recyclerView.setAdapter(recAdapter);

        return view;
    }

}
