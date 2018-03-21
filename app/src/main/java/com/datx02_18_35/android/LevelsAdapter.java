package com.datx02_18_35.android;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelProgression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-18.
 */

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> implements View.OnClickListener {
    private List<Map.Entry<Level,LevelProgression>> levels;
    Levels activity;


    LevelsAdapter(ArrayList<Map.Entry<Level,LevelProgression>> levels, Levels activity){
        this.levels=levels;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_level, parent,false);
        return new LevelsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = levels.get(position).getKey().title;
        ((TextView) holder.cardView.findViewById(R.id.card_level_title)).setText(title);
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int position= (int)view.getTag();
        Intent intent = new Intent(view.getContext(),GameBoard.class);
        intent.putExtra("levelInt",position);
        System.out.println("clickedyclick");
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(levels==null){
            return 0;
        }
        else{
            return levels.size();
        }

    }

    public void updateLevels(final List<Map.Entry<Level, LevelProgression>> levelList) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                levels=levelList;
                notifyDataSetChanged();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}
