package com.bkav.android.music.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract  class BaseCursorAdapter <V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    public static Cursor mCursor;
    public static boolean mDataValid;
    private int mRowIDColumn;

    public abstract void onBindViewHolder(V holder, Cursor cursor);

    public BaseCursorAdapter() {
        this.mRowIDColumn = 0;
    }

    public BaseCursorAdapter(Cursor c) {
        setHasStableIds(true);
        swapCursor(c);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {

        if (!mDataValid) {
            throw new IllegalStateException("Cannot bind view holder when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position + " when trying to bind view holder");
        }

        onBindViewHolder(holder, mCursor);
    }

    @Override
    public int getItemCount() {
        if (mDataValid) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if (!mDataValid) {
            throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position + " when trying to get an item id");
        }

        return mCursor.getLong(mRowIDColumn);
    }

    public static Cursor getItem(int position) {
        if (!mDataValid) {
            throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position + " when trying to get an item id");
        }
        return mCursor;
    }

    public void swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return;
        }

        if (newCursor != null) {
            mCursor = newCursor;
            mDataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
            mCursor = null;
            mRowIDColumn = -1;
            mDataValid = false;
        }
    }
}