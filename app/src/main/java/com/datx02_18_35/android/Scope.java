package com.datx02_18_35.android;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import game.logic_game.R;

public class Scope extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();


        ft.replace(R.id.game_right_side, new FragmentScopeActions());
        ft.replace(R.id.game_left_side , new FragmentScopeCards()).commit();

        layout = (FrameLayout)findViewById(R.id.fragment_container);
        ft.replace(R.id.fragment_container, new FragmentInventory());
        layout.setVisibility(View.GONE);

        Button inv_button = (Button) findViewById(R.id.inventory_button); //grab a view and convert it to a button class
        inv_button.setOnClickListener(this);

        Button close_button = (Button) findViewById(R.id.close_button); //grab a view and convert it to a button class
        close_button.setOnClickListener(this);

        Button resolve_button = (Button) findViewById(R.id.resolve_button); //grab a view and convert it to a button class
        resolve_button.setOnClickListener(this);

        //Set toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.setSupportActionBar(toolbar);
        TextView mTextView = (TextView) findViewById(R.id.toolbar_text);
        mTextView.setText("Scope 1");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu); //add more items under dir menu -> toolbar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()){
            case R.id.item_assumption:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft =fm.beginTransaction();
                int fragcount = fm.getBackStackEntryCount();
                TextView mTextView = (TextView) findViewById(R.id.toolbar_text);
                mTextView.setText("Scope "+(fragcount+2));
                ft.replace(R.id.game_left_side , new FragmentScopeCards()).addToBackStack("").commit(); //Option is to open new intent
                break;
        }
        return false;
    }
    public void showInventory(){
        if (layout.isShown()) {
            layout.setVisibility(View.GONE);

        }
        else {
            layout.setVisibility(View.VISIBLE);
            
        }
    }
    @Override
    public void onBackPressed(){
        if(layout.isShown()) {
            layout.setVisibility(View.GONE);
        }
        else {
            //popScope();           säg till controllern att pop scope

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft =fm.beginTransaction();
            int fragcount = fm.getBackStackEntryCount();
            TextView mTextView = (TextView) findViewById(R.id.toolbar_text);
            if (fragcount!=0){
                mTextView.setText("Scope "+(fragcount));
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inventory_button: {
                showInventory();
                break;
            }
            case R.id.close_button: {
                onBackPressed();
                break;
            }
        }
    }

}
