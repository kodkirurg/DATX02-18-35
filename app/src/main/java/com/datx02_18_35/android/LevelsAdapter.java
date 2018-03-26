package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelCollection;
import com.datx02_18_35.model.game.LevelProgression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-18.
 */


public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> implements View.OnClickListener  {
    private List<Level> levelList;
    private Map<Level, LevelProgression> progressionMap;
    private final Levels levelsActivity;


    LevelsAdapter(Levels levelsActivity){
        this.levelsActivity = levelsActivity;
        this.levelList = null;
        this.progressionMap = null;
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

        String title = levelList.get(position).title;
        ((TextView) holder.cardView.findViewById(R.id.card_level_title)).setText(title);
        String description = levelList.get(position).description;
        ((TextView) holder.cardView.findViewById(R.id.card_level_description)).setText(description);
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        if(levelList ==null){
            return 0;
        }
        else{
            return levelList.size();
        }

    }

    public void updateLevels(final LevelCollection _levelCollection, final Map<Level, LevelProgression> _progressionMap) {
        this.levelsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                levelList = new ArrayList<>();
                for (Level level : _levelCollection.getAllLevels()) {
                    levelList.add(level);
                }
                progressionMap = _progressionMap;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        levelsActivity.startLevel(levelList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}
