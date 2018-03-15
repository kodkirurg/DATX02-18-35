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
import com.datx02_18_35.model.game.Level;

import java.util.ArrayList;

import game.logic_game.R;


/**
 * Created by Magnu on 2018-03-13.
 */

public class FragmentSandboxCards extends Fragment {

    public RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;
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


        list.addAll(Level.exampleLevel.propositions);
        recAdapter = new SandboxCardsAdapter(list);
        //add drag and drop
        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback((SandboxCardsAdapter) recAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);


        //((Game)getActivity()).ready.release(1);
        return view;
    }

}
