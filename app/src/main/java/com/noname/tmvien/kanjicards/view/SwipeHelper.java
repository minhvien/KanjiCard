package com.noname.tmvien.kanjicards.view;

import android.animation.Animator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.noname.tmvien.kanjicards.utils.AnimationUtils;

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

    private GestureDetector gestureDetector;


    private float mRotateDegrees = SwipeCardView.DEFAULT_SWIPE_ROTATION;
    private int mAnimationDuration = SwipeCardView.DEFAULT_ANIMATION_DURATION;
    private int mSwipeThreshold = 100;

    public SwipeHelper(SwipeCardView SwipeCardView) {
        mSwipeCardView = SwipeCardView;
        gestureDetector = new GestureDetector(mSwipeCardView.getContext(), new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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

    private void touchEventItem() {
        if (!mListenForTouchEvents) return;
        mSwipeCardView.onTouchCardView();
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

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick();
            return super.onSingleTapUp(e);
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            swipeViewToRight();
                        } else {
                            swipeViewToLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            swipeViewToBottom();
                        } else {
                            swipeViewToTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void swipeViewToTop() {
    }

    public void swipeViewToBottom() {
    }

    public void onClick() {
        mSwipeCardView.onTouchCardView();
    }
}
