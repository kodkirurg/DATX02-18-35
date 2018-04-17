package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.level.LevelParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import game.logic_game.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init controller & model
        initController();

        //Add listener
        Button start_button = findViewById(R.id.start_button); //grab a view and convert it to a button class
        start_button.setOnClickListener(this); //this indicates that the onClick will be called
        Button quit_button = findViewById(R.id.quit_button);
        quit_button.setOnClickListener(this);

    }

    private void initController() {
        // Read model config files
        if (Controller.isInitialized()) {
            return;
        }
        ModelAssetReader modelAssetReader = new ModelAssetReader(getApplicationContext().getAssets());
        Map<String, String> modelAssets;
        try {
            modelAssets = modelAssetReader.read();
        } catch (IOException e) {
            e.printStackTrace();
            modelAssets = new HashMap<>();
        }

        try {
            Controller.init(modelAssets, Tools.getUserData(getApplicationContext()));
        } catch (LevelParseException e) {
            e.printStackTrace();
        } catch (ExpressionParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button: {
                Intent intent = new Intent(this, Levels.class); //create intent
                startActivity(intent); //start intent
                break;
            }
            case R.id.quit_button: {
                this.finish();
                System.exit(0);
                break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        this.finish();
        System.exit(0);
    }
}
