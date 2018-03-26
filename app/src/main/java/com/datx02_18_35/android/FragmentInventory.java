package com.datx02_18_35.android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestInventoryAction;
import com.datx02_18_35.model.expression.Expression;


import java.util.ArrayList;
import java.util.Iterator;

import game.logic_game.R;


/**
 * Created by Magnus on 2018-02-14.
 */


public class FragmentInventory extends Fragment {
    private RecyclerView invRecyclerView;
    private RecyclerView.Adapter invRecAdapter;
    private RecyclerView.LayoutManager invRecLayoutManager;
    private ArrayList<Expression> inventoryList = new ArrayList<Expression>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_inventory, container, false);
/*
        int spanCount;
        int widthDP=Math.round(Tools.getWidthDp(getActivity().getApplicationContext())) - 130*2;
        for (spanCount=0; 130*spanCount < widthDP ;spanCount++);

        Iterator<Iterable<Expression>> invIterator = GameBoard.inventories.iterator();

        while (invIterator.hasNext()) {
            Iterable<Expression> inventory = invIterator.next();
            Iterator<Expression> expressionIterator = inventory.iterator();
            while (expressionIterator.hasNext()) {
                inventoryList.add(expressionIterator.next());
            }
        }



        invRecyclerView = (RecyclerView) frag.findViewById(R.id.inv_recycler_view);
        // use a grid layout manager
        invRecLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        invRecyclerView.setLayoutManager(invRecLayoutManager);
        invRecAdapter = new InventoryAdapter(inventoryList, this);
        invRecyclerView.setAdapter(invRecAdapter);
*/
        return frag;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here


    }


}
