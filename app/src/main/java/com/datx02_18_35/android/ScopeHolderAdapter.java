package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestMoveFromInventoryAction;
import com.datx02_18_35.model.GameException;
import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;
import java.util.List;

import game.logic_game.R;

public class ScopeHolderAdapter extends RecyclerView.Adapter<ScopeHolderAdapter.ViewHolder> implements View.OnClickListener {
    private GameBoard activity;
    private int count;
    private List<Expression> hypothesis;
    private List<Expression> assumptions;
    private List<List<Expression>> inventories;

    private final static int ASSUMPTION_INDEX_OFFSET = 2;
    private final static int INVENTORIES_INDEX_OFFSET = 1;

    public ScopeHolderAdapter(GameBoard activity){
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ScopeHolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position < ASSUMPTION_INDEX_OFFSET){
            ConstraintSet set = new ConstraintSet();
            holder.rightContainer.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
            set.clone(holder.layout);
            // The following breaks the connection.
            set.clear(R.id.inventory_right_side, ConstraintSet.LEFT);
            set.clear(R.id.inventory_left_side);

            set.connect(R.id.inventory_right_side, ConstraintSet.LEFT, R.id.inventory_bottom, ConstraintSet.LEFT, 0);
            set.applyTo(holder.layout);
            holder.leftContainer.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.GONE);

        }
        else {
            holder.cardView.setOnClickListener(this);
            holder.setIsRecyclable(false);
            holder.cardView.setTag(position-ASSUMPTION_INDEX_OFFSET);
            holder.cardView.setTag(R.string.viewholders,holder);
            holder.cardView.setBackgroundColor(Color.GRAY);
            if(!holder.alreadyBound) {
                CardInflator.inflate((CardView) holder.cardView,assumptions.get(position-ASSUMPTION_INDEX_OFFSET),120,170,false);
                holder.alreadyBound = true;
            }

        }

        if (position == 0) {
            holder.assumptionText.setText("");
            holder.scopeText.setText("Hypothesis");
        } else if (position == 1) {
            holder.assumptionText.setText("");
            holder.scopeText.setText("Inventory");
        } else {
            holder.assumptionText.setText("Assumption #" + (position-ASSUMPTION_INDEX_OFFSET));
            holder.scopeText.setText("Inventory");
        }

        //holder.assumptionText.setText("Assumption");
        //holder.scopeText.setText(section.get(position));

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecycleView.setLayoutManager(hs_linearLayout);
        holder.childRecycleView.setHasFixedSize(false);
        List<Expression> expressions;
        if (position < INVENTORIES_INDEX_OFFSET) {
            expressions = hypothesis;
        }
        else {
            expressions = inventories.get(position - INVENTORIES_INDEX_OFFSET);
        }
        InventoryAdapter inventoryChildAdapter = new InventoryAdapter(expressions, this.activity);
        holder.childRecycleView.setAdapter(inventoryChildAdapter);


    }
    public void updateInventory(final Iterable<Expression> _hypothesis,
                                final Iterable<Expression> _assumptions,
                                final Iterable<Iterable<Expression>> _inventories) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                hypothesis = new ArrayList<>();
                for (Expression expr : _hypothesis) {
                    hypothesis.add(expr);
                }

                count = 2;
                assumptions = new ArrayList<>();
                for (Expression expr : _assumptions) {
                    assumptions.add(expr);
                    count += 1;
                }
                inventories = new ArrayList<>();
                for (Iterable<Expression> inventory : _inventories) {
                    List<Expression> inventoryList = new ArrayList<>();
                    inventories.add(inventoryList);
                    for (Expression expr : inventory) {
                        inventoryList.add(expr);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onClick(View view) {
        if (view!=null) {
            Expression selectedCard = assumptions.get((int) view.getTag());
            try {
                Controller.getSingleton().handleAction(new RequestMoveFromInventoryAction(GameBoard.boardCallback,selectedCard));
            } catch (GameException e) {
                e.printStackTrace();
            }
            Fx.blink_animation(view.getContext(), view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView childRecycleView;
        View cardView;
        TextView assumptionText;
        TextView scopeText;
        LinearLayout leftContainer;
        LinearLayout rightContainer;
        ConstraintLayout layout;
        boolean alreadyBound=false;


        ViewHolder(View itemView) {
            super(itemView);
            childRecycleView =  itemView.findViewById(R.id.recycler_scope);
            assumptionText = itemView.findViewById(R.id.assumption_text);
            cardView = itemView.findViewById(R.id.assumption_card);
            scopeText = itemView.findViewById(R.id.scope_text);
            leftContainer = itemView.findViewById(R.id.inventory_left_side);
            rightContainer = itemView.findViewById(R.id.inventory_right_side);
            layout = itemView.findViewById(R.id.inventory_bottom);
        }
    }
}
