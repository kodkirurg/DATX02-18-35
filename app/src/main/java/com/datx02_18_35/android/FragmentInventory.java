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
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;


/**
 * Created by Magnus on 2018-02-14.
 */


public class FragmentInventory extends Fragment implements View.OnClickListener  {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.inventory_frag, container, false);

        recyclerView = (RecyclerView) frag.findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(recLayoutManager);


        // specify an adapter (see also next example)
        ArrayList<Expression> list = new ArrayList<Expression>();
        ExpressionFactory exprFact = ExpressionFactory.getSingleton();
        Expression p1 = exprFact.createProposition("P");
        Expression q1 = exprFact.createProposition("Q");
        Expression r1 = exprFact.createProposition("R");
        Expression c4 = exprFact.createOperator(OperatorType.IMPLICATION, p1,q1);
        Expression c5 = exprFact.createOperator(OperatorType.IMPLICATION, r1,q1);

        list.add(p1);


        recAdapter = new RecyclerAdapter(list);


        //add drag and drop
        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback((RecyclerAdapter) recAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);

     //   Button addTo_button = (Button)  container.getRootView().findViewById(R.id.addTo_button); //grab a view and convert it to a button class
     //   addTo_button.setOnClickListener(this);

        return frag;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here


    }
    public void updateInventory(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTo_button: {
                /*if (something_is_clicked){
                    take the clicked item and send to scope board
                }*/
                break;
            }
        }
    }
}
