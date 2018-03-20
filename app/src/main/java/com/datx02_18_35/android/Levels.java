package com.datx02_18_35.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import game.logic_game.R;

public class Levels extends AppCompatActivity {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    LevelsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        recyclerView = (RecyclerView) findViewById(R.id.levels_recyclerview);


        //dynamic span count later
        gridLayoutManager = new GridLayoutManager(getApplication(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //add levels


        adapter = new LevelsAdapter(new ArrayList<>(),this);
        recyclerView.setAdapter(adapter);


    }
}
