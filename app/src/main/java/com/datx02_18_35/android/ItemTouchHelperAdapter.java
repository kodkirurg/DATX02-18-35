package com.datx02_18_35.android;

/**
 * Created by raxxor on 2018-02-11.
 */


public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
