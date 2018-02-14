package com.datx02_18_35.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import java.util.ArrayList;

import game.logic_game.R;

public class Game extends AppCompatActivity implements OnStartDragListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = (RecyclerView) findViewById(R.id.game_recycler_view);
        // use a grid layout manager
        recLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        ArrayList list = new ArrayList();
        list.add(0, "test"); //edit this to remove and add elements
        list.add(1, "test"); //edit this to remove and add elements
        list.add(2, "test"); //edit this to remove and add elements
        list.add(3, "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements




        recAdapter = new RecyclerAdapter(list);

        //add drag and drop
        ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback((RecyclerAdapter) recAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recAdapter);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
