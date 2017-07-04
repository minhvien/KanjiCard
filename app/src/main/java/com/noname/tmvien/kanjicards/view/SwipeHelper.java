package com.noname.tmvien.kanjicards.view;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

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
    private float mOpacityEnd = SwipeCardView.DEFAULT_SWIPE_OPACITY;
    private int mAnimationDuration = SwipeCardView.DEFAULT_ANIMATION_DURATION;

    public SwipeHelper(SwipeCardView SwipeCardView) {
        mSwipeCardView = SwipeCardView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if(!mListenForTouchEvents || !mSwipeCardView.isEnabled()) {
//                    return false;
//                }
//
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                mSwipeCardView.onSwipeStart();
//                mPointerId = event.getPointerId(0);
//                mDownX = event.getX(mPointerId);
//                mDownY = event.getY(mPointerId);

                mInitialX = event.getX(mPointerId);
                return true;

            case MotionEvent.ACTION_MOVE:
//                int pointerIndex = event.findPointerIndex(mPointerId);
//                if (pointerIndex < 0) return false;
//
//                float dx = event.getX(pointerIndex) - mDownX;
//                float dy = event.getY(pointerIndex) - mDownY;
//
//                float newX = mObservedView.getX() + dx;
//                float newY = mObservedView.getY() + dy;
//
//                mObservedView.setX(newX);
//                mObservedView.setY(newY);
//
//                float dragDistanceX = newX - mInitialX;
//                float swipeProgress = Math.min(Math.max(
//                        dragDistanceX / mSwipeCardView.getWidth(), -1), 1);
//
//                mSwipeCardView.onSwipeProgress(swipeProgress);
//
//                if (mRotateDegrees > 0) {
//                    float rotation = mRotateDegrees * swipeProgress;
//                    mObservedView.setRotation(rotation);
//                }
//
//                if (mOpacityEnd < 1f) {
//                    float alpha = 1 - Math.min(Math.abs(swipeProgress * 2), 1);
//                    mObservedView.setAlpha(alpha);
//                }


                return true;

            case MotionEvent.ACTION_UP:
//                v.getParent().requestDisallowInterceptTouchEvent(false);
//                mSwipeCardView.onSwipeEnd();
//                checkViewPosition();

                mDownX = event.getX(mPointerId);
                float tmp = mDownX - mInitialX;
                if(Math.abs(tmp) > 100 && mDownX > mInitialX){
                    Log.e("AAAAAA", " turn of right");
                } else if(Math.abs(tmp) > 100 && mDownX < mInitialX){
                    Log.e("AAAAAA", " turn of left");
                }
                return true;

        }

        return false;
    }

    private void checkViewPosition() {
        if(!mSwipeCardView.isEnabled()) {
            resetViewPosition();
            return;
        }

        float viewCenterHorizontal = mObservedView.getX() + (mObservedView.getWidth() / 2);
        float parentFirstThird = mSwipeCardView.getWidth() / 3f;
        float parentLastThird = parentFirstThird * 2;

        if (viewCenterHorizontal < parentFirstThird &&
                mSwipeCardView.getAllowedSwipeDirections() != SwipeCardView.SWIPE_DIRECTION_ONLY_RIGHT) {
            swipeViewToLeft(mAnimationDuration / 2);
        } else if (viewCenterHorizontal > parentLastThird &&
                mSwipeCardView.getAllowedSwipeDirections() != SwipeCardView.SWIPE_DIRECTION_ONLY_LEFT) {
            swipeViewToRight(mAnimationDuration / 2);
        } else {
            resetViewPosition();
        }
    }

    private void resetViewPosition() {
        mObservedView.animate()
                .x(mInitialX)
                .y(mInitialY)
                .rotation(0)
                .alpha(1)
                .setDuration(mAnimationDuration)
                .setInterpolator(new OvershootInterpolator(1.4f))
                .setListener(null);
    }

    private void swipeViewToLeft(int duration) {
        if (!mListenForTouchEvents) return;
        mListenForTouchEvents = false;
        mObservedView.animate().cancel();
        mObservedView.animate()
                .x(-mSwipeCardView.getWidth() + mObservedView.getX())
                .rotation(-mRotateDegrees)
                .alpha(0f)
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
        mListenForTouchEvents = false;
        mObservedView.animate().cancel();
        mObservedView.animate()
                .x(mSwipeCardView.getWidth() + mObservedView.getX())
                .rotation(mRotateDegrees)
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimationUtils.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSwipeCardView.onViewSwipedToRight();
                    }
                });
    }

    public void registerObservedView(View view, float initialX, float initialY) {
        if (view == null) return;
        mObservedView = view;
        mObservedView.setOnTouchListener(this);
        mInitialX = initialX;
        mInitialY = initialY;
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

    public void setOpacityEnd(float alpha) {
        mOpacityEnd = alpha;
    }

    public void swipeViewToLeft() {
        swipeViewToLeft(mAnimationDuration);
    }

    public void swipeViewToRight() {
        swipeViewToRight(mAnimationDuration);
    }

}
