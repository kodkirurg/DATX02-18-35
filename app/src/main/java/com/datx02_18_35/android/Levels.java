package com.datx02_18_35.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.model.game.Level;

import java.util.ArrayList;

import game.logic_game.R;

public class Levels extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LevelsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        recyclerView = (RecyclerView) findViewById(R.id.levels_recyclerview);


        //dynamic span count later
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //add levels
        ArrayList<Level> list = new ArrayList<>();
        list.addAll(Controller.getSingleton().getLevels());
        list.addAll(Controller.getSingleton().getLevels());
        list.addAll(Controller.getSingleton().getLevels());
        list.addAll(Controller.getSingleton().getLevels());


        adapter = new LevelsAdapter(list,this);
        recyclerView.setAdapter(adapter);


    }
}
