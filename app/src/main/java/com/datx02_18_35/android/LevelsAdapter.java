package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.userdata.LevelCategoryProgression;
import com.datx02_18_35.model.userdata.LevelProgression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-18.
 */


public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> implements View.OnClickListener  {
    private List<Level> dataSet;
    private final Levels levelsActivity;
    Map<Level, LevelProgression> levelProgressionMap;
    LevelCategoryProgression levelCategoryProgression;


    LevelsAdapter(Levels levelsActivity){
        this.levelsActivity = levelsActivity;
        this.dataSet = new ArrayList<>();
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

        Level levelInCard = dataSet.get(position);
        if (levelCategoryProgression.status == LevelCategoryProgression.Status.LOCKED) {
            holder.cardView.setEnabled(false);
            holder.cardView.setClickable(false);
            holder.cardView.setBackgroundColor(holder.cardView.getResources().getColor(R.color.Gray));
        }
        else {
            LevelProgression levelProgression = levelProgressionMap.get(levelInCard);
            if (levelProgression != null && levelProgression.completed) {
                holder.cardView.setBackgroundColor(holder.cardView.getResources().getColor(R.color.Green));
            }
            else {
                holder.cardView.setBackgroundColor(holder.cardView.getResources().getColor(R.color.Red));
            }
        }
        

        String title = dataSet.get(position).title;
        ((TextView) holder.cardView.findViewById(R.id.card_level_title)).setText(title);
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(this);
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

    public void updateLevels(final LevelCategory levelCategory, Map<Level, LevelProgression> levelProgressionMap, LevelCategoryProgression levelCategoryProgression) {
        this.levelCategoryProgression=levelCategoryProgression;
        this.levelProgressionMap=levelProgressionMap;
        this.levelsActivity.runOnUiThread(new Runnable() {

            // levelcollection is category
            @Override
            public void run() {
                dataSet.clear();
                for (Level level : levelCategory.getLevels()) {
                    dataSet.add(level);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        levelsActivity.startLevel(dataSet.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}
