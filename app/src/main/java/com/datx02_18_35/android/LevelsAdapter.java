package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> implements View.OnClickListener  {
    private List<Map.Entry<Level,LevelProgression>> dataSet;
    Levels activity;


    LevelsAdapter(ArrayList<Map.Entry<Level,LevelProgression>> levels, Levels activity){
        this.dataSet =levels;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_level, parent,false);
        cardView.setOnClickListener(this);
        return new LevelsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setTag(position);

        String title = dataSet.get(position).getKey().title;
        ((TextView) holder.cardView.findViewById(R.id.card_level_title)).setText(title);
    }

    @Override
    public int getItemCount() {
        if(dataSet ==null){
            return 0;
        }
        else{
            return dataSet.size();
        }

    }

    public void updateLevels(final List<Map.Entry<Level, LevelProgression>> levelList) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet = levelList;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        activity.startLevel(dataSet.get(position).getKey());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}
