package com.datx02_18_35.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import game.logic_game.R;

public class Game extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = (RecyclerView) findViewById(R.id.game_recycler_view);
        // use a linear layout manager
        recLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        recAdapter = new RecyclerAdapter(null);
        recyclerView.setAdapter(recAdapter);
    }

}
