package com.datx02_18_35.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import game.logic_game.R;

public class Game extends AppCompatActivity  {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test123","test");
        setContentView(R.layout.activity_game);

        Log.d("test123","test");

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        Fragment_board_cards frag = new Fragment_board_cards();


        Log.d("test123","test");
        ft.replace(R.id.game_left_side , frag).commit();

    }

}
