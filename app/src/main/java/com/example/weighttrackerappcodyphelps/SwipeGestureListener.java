package com.example.weighttrackerappcodyphelps;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 120;

    private Context context;

    public SwipeGestureListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("SwipeGesture", "onScroll: Drag detected");

        // Swipe right
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
            Log.d("SwipeGesture", "onScroll: Swipe right detected");
            Intent intent = new Intent(context, SMSPermission.class);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}

