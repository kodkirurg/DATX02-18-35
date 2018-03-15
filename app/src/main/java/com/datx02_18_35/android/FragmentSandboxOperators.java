package com.datx02_18_35.android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.OperatorType;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-13.
 */

public class FragmentSandboxOperators extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ArrayList<OperatorType> list = new ArrayList<OperatorType>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandbox_operator,
                container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.sandboxRight_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(recLayoutManager);



        list.add(OperatorType.IMPLICATION);
        list.add(OperatorType.DISJUNCTION);
        list.add(OperatorType.CONJUNCTION);
        recAdapter = new SandboxOperatorAdapter(list);


        recyclerView.setAdapter(recAdapter);

        return view;
    }

}
