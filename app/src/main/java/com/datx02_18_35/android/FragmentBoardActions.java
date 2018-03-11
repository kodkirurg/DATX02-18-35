package com.datx02_18_35.android;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import game.logic_game.R;




public class FragmentBoardActions extends Fragment {
    private RecyclerView recyclerView;
    public RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    public ArrayList<Rule> collection = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_actions,
                container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recLayoutManager);

        collection.add(null);
        //attach list to adapter
        recAdapter = new RuleAdapter(collection);


        //settings for this fragment
        EditItemTouchHelperCallback localBehaviour = new EditItemTouchHelperCallback((RuleAdapter) recAdapter);
        localBehaviour.isLongPressDragEnabled = true;
        localBehaviour.rules = true;

        //create touch helper and attach
        itemTouchHelper = new ItemTouchHelper(localBehaviour);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //attach adapter
        recyclerView.setAdapter(recAdapter);


        ((Game)getActivity()).ready.release(1);
        return view;
    }

    public void updateActions(final Collection<Rule> data){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                collection.clear();
                collection.addAll(data);
                recAdapter.notifyDataSetChanged();
            }
        });



    }


}
