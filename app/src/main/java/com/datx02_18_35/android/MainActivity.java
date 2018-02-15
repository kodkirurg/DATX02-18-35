package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import game.logic_game.R;
import com.datx02_18_35.lib.logicmodel.LogicModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add listener
        Button start_button = (Button) findViewById(R.id.start_button); //grab a view and convert it to a button class
        start_button.setOnClickListener(this); //this indicates that the onClick will be called
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button: {
                LogicModel.main(null); // Testing :)
                Intent intent = new Intent(this, Game.class); //create intent
                startActivity(intent); //start intent
                break;
            }
        }
    }
}
