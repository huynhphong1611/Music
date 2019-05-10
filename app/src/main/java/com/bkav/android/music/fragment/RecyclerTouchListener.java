package com.bkav.android.music.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    public interface ClickListener{
        public void onClick(View view,int position);
    }



    private GestureDetector mGestureDetector;
    ClickListener clickListener;
    public RecyclerTouchListener (Context context,final RecyclerView recyclerView
            ,final ClickListener clickListener){
        this.clickListener=clickListener;
        mGestureDetector =new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
    }
    @Override
    public boolean onInterceptTouchEvent( RecyclerView recyclerView,  MotionEvent e) {
        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && mGestureDetector.onTouchEvent(e))
            clickListener.onClick(child, recyclerView.getChildAdapterPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
