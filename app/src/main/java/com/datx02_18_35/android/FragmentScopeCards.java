package com.datx02_18_35.android;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;

import game.logic_game.R;

public class FragmentScopeCards extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private ArrayList<Expression> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(null,
                container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        list = new ArrayList<Expression>();




        recAdapter = new GameCardAdapter(list);

        return view;
    }


    public void addToBoard(/*Expression newCard*/){
        //list.add(newCard);
        //recAdapter.notifyDataSetChanged();
    }
    public void updateCards(ArrayList<Expression> newList){
        //list=newList;
        //recAdapter.notifyDataSetChanged();
    }



}
