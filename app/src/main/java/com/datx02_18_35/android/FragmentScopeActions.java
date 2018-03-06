package com.datx02_18_35.android;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;

import game.logic_game.R;




public class FragmentScopeActions extends Fragment {
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
        ArrayList list = new ArrayList();

        ExpressionFactory expressionFactory = ExpressionFactory.getSingleton();
        Collection collections = new ArrayList<>();
        collections.add(expressionFactory.createProposition("P"));
        collections.add(expressionFactory.createProposition("Q"));

        Collection legalRules =  Rule.getLegalRules(null,collections); // conunction introd
        Object[] rules = legalRules.toArray();



        recAdapter = new RecyclerAdapter(list);


        //settings for this fragment
        EditItemTouchHelperCallback localBehavoir = new EditItemTouchHelperCallback((RecyclerAdapter) recAdapter);
        localBehavoir.isLongPressDragEnabled = false;


        //add drag and drop
        ItemTouchHelper.Callback callback = localBehavoir;


        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);



        return view;
    }
    public void updateActions() {
        //list=(all the actions);
        //recAdapter.notifyDataSetChanged();
    }

}
