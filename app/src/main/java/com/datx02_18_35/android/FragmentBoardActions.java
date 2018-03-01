package com.datx02_18_35.android;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.lib.logicmodel.expression.Expression;
import com.datx02_18_35.lib.logicmodel.expression.ExpressionFactory;
import com.datx02_18_35.lib.logicmodel.expression.Rule;
import com.datx02_18_35.lib.logicmodel.expression.RuleType;

import java.util.ArrayList;

import game.logic_game.R;




public class FragmentBoardActions extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_actions,
                container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<Rule> list = new ArrayList<Rule>();

        ExpressionFactory exprFact = ExpressionFactory.getSingleton();
        Expression r1 = exprFact.createProposition("R");





        recAdapter = new RuleAdapter(list);


        //settings for this fragment
        EditItemTouchHelperCallback localBehavoir = new EditItemTouchHelperCallback((RuleAdapter) recAdapter);
        localBehavoir.isLongPressDragEnabled = true;
        localBehavoir.rules = true;


        //add drag and drop
        ItemTouchHelper.Callback callback = localBehavoir;


        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);



        return view;
    }

}
