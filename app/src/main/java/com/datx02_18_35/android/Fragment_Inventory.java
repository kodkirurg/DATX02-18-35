package com.datx02_18_35.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.datx02_18_35.lib.logicmodel.Inventory;

import java.io.Serializable;
import java.util.ArrayList;

import game.logic_game.R;


/**
 * Created by Magnus on 2018-02-14.
 */

public class Fragment_Inventory extends Fragment implements Serializable {
    public boolean open = false;
    private Fragment_board_cards cardBoard = new Fragment_board_cards();
    private Inventory cards;

    public void Fragment_Inventory(Inventory inven){
        cards = inven;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.inventory_frag, container, false);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();

        ft.replace(R.id.view_rec , cardBoard).commit();



        return frag;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //do your stuff for your fragment here


    }
    public void updateInventory(){

    }

}
