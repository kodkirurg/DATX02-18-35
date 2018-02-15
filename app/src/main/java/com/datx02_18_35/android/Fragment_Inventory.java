package com.datx02_18_35.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import game.logic_game.R;

/**
 * Created by Magnus on 2018-02-14.
 */

public class Fragment_Inventory extends Fragment {
public boolean open = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.inventory_frag, container, false);
      //  RecyclerView rec = frag.findViewById(R.id.);



        return frag;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here


    }
}
