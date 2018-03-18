package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.RequestStartNewSessionAction;
import com.datx02_18_35.model.expression.ExpressionFactory;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelParseException;

import java.util.ArrayList;

import game.logic_game.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add listener
        Button start_button = (Button) findViewById(R.id.start_button); //grab a view and convert it to a button class
        start_button.setOnClickListener(this); //this indicates that the onClick will be called
        Button quit_button = (Button) findViewById(R.id.quit_button);
        quit_button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //TODO: Pass list of level files as Strings
            Controller.init(Level.exampleLevels, null);
            Controller.getSingleton().start();
        } catch (LevelParseException e) {
            //TODO: Handle this properly
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button: {
                Intent intent = new Intent(this, GameBoard.class); //create intent
                startActivity(intent); //start intent
                break;
            }
            case R.id.quit_button : {
                finish();
                break;
            }
        }
    }
}
