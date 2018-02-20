package com.datx02_18_35.android;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

import game.logic_game.R;

public class Scope extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;
    private FragmentInventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope);
        inventory = Game.getInventory();

        recyclerView = (RecyclerView) findViewById(R.id.game_recycler_view);
        // use a linear layout manager
        recLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(recLayoutManager);

        // specify an adapter (see also next example)
        ArrayList list = new ArrayList();
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements
        list.add( "test"); //edit this to remove and add elements

        recAdapter = (RecyclerAdapter) new RecyclerAdapter(list);
        recyclerView.setAdapter(recAdapter);

        Button inv_button = (Button) findViewById(R.id.inventory_button); //grab a view and convert it to a button class
        inv_button.setOnClickListener(this);

        Button close_button = (Button) findViewById(R.id.close_button); //grab a view and convert it to a button class
        close_button.setOnClickListener(this);

    }
    public void giveInventory(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FrameLayout layout = (FrameLayout)findViewById(R.id.fragment_container);
        ft.replace(R.id.fragment_container, inventory);
        if (inventory.open ==true) {
            layout.setVisibility(View.GONE);
            inventory.open = false;
        }
        else {
            layout.setVisibility(View.VISIBLE);
            inventory.open = true;
        }
// alternatively add it with a tag
// trx.add(R.id.your_placehodler, new YourFragment(), "detail");
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inventory_button: {
                giveInventory();
                break;
            }
            case R.id.close_button: {
                finish();
            }
        }
    }
}
