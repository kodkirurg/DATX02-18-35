package com.datx02_18_35.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;


import game.logic_game.R;

public class Game extends AppCompatActivity  {

    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();

        ft.replace(R.id.game_right_side, new Fragment_board_actions());
        ft.replace(R.id.game_left_side , new Fragment_board_cards()).commit();


        //Set toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.setSupportActionBar(toolbar);

    }


}
