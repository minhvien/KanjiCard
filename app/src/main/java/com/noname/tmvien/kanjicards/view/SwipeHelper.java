package com.noname.tmvien.kanjicards.view;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;

import com.noname.tmvien.kanjicards.utils.AnimationUtils;
import com.noname.tmvien.kanjicards.utils.Log;

/**
 * Created by tmvien on 7/3/17.
 */

class SwipeHelper implements View.OnTouchListener {

    private final SwipeCardView mSwipeCardView;
    private View mObservedView;

    private boolean mListenForTouchEvents;
    private float mDownX;
    private float mDownY;
    private float mInitialX;
    private float mInitialY;
    private int mPointerId;


    private float mRotateDegrees = SwipeCardView.DEFAULT_SWIPE_ROTATION;
    private int mAnimationDuration = SwipeCardView.DEFAULT_ANIMATION_DURATION;
    private int mSwipeThreshold = 100;

    public SwipeHelper(SwipeCardView SwipeCardView) {
        mSwipeCardView = SwipeCardView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialX = event.getX(mPointerId);
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                mDownX = event.getX(mPointerId);
                float tmp = mDownX - mInitialX;
                if(Math.abs(tmp) > mSwipeThreshold && mDownX > mInitialX){
                    Log.e("Swipe", "Swipe right");
                    swipeViewToRight();
                } else if(Math.abs(tmp) > mSwipeThreshold && mDownX < mInitialX){
                    Log.e("Swipe", "Swipe left");
                    swipeViewToLeft();
                }
                return true;
        }

        return false;
    }
    
    private void swipeViewToLeft(int duration) {
        if (!mListenForTouchEvents) return;
        if (mSwipeCardView.isLastCardDisplayed()) return;
        mListenForTouchEvents = false;
        mObservedView.animate().cancel();
        mObservedView.animate()
                .x(-mSwipeCardView.getWidth() + mObservedView.getX())
                .rotation(-mRotateDegrees)
                .alpha(1f)
                .setDuration(duration)
                .setListener(new AnimationUtils.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSwipeCardView.onViewSwipedToLeft();
                    }
                });
    }

    private void swipeViewToRight(int duration) {
        if (!mListenForTouchEvents) return;
        if (mSwipeCardView.isFirstCardDisplayed()) return;
        mListenForTouchEvents = false;
        mSwipeCardView.onViewSwipedToRight();
    }

    public void registerObservedView(View view) {
        if (view == null) return;
        mObservedView = view;
        mObservedView.setOnTouchListener(this);
        mListenForTouchEvents = true;
    }

    public void unregisterObservedView() {
        if (mObservedView != null) {
            mObservedView.setOnTouchListener(null);
        }
        mObservedView = null;
        mListenForTouchEvents = false;
    }

    public void setAnimationDuration(int duration) {
        mAnimationDuration = duration;
    }

    public void setRotation(float rotation) {
        mRotateDegrees = rotation;
    }

    public void swipeViewToLeft() {
        swipeViewToLeft(mAnimationDuration);
    }

    public void swipeViewToRight() {
        swipeViewToRight(mAnimationDuration);
    }

}
