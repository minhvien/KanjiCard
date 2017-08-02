package com.noname.tmvien.kanjicards.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.SwipeCardAdapter;
import com.noname.tmvien.kanjicards.utils.Log;

import java.util.Random;

/**
 * Created by tmvien on 7/3/17.
 */

public class SwipeCardView extends ViewGroup {

    private static String TAG = SwipeCardView.class.getSimpleName();


    public static final int DEFAULT_ANIMATION_DURATION = 300;
    public static final int DEFAULT_VISIBLE_CARD = 3;
    public static final int DEFAULT_CARD_ROTATION = 4;
    public static final float DEFAULT_SWIPE_ROTATION = 30f;
    public static final int DEFAULT_VIEW_SPACING = 12;
    public static final float DEFAULT_SCALE_FACTOR = 1f;
    public static final boolean DEFAULT_DISABLE_HW_ACCELERATION = true;


    private Adapter mAdapter;
    private Random mRandom;

    private int mAnimationDuration;
    private int mCurrentViewIndex;
    private int mNumberOfVisibleViews;
    private int mViewSpacing;
    private int mViewRotation;
    private float mSwipeRotation;
    private float mScaleFactor;
    private boolean mDisableHwAcceleration;

    private View mTopView;
    private SwipeHelper mSwipeHelper;
    private DataSetObserver mDataObserver;

    public SwipeCardView(Context context) {
        this(context, null);
    }

    public SwipeCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(attrs);
        initialize();
    }

    private void readAttributes(AttributeSet attributeSet) {
        TypedArray attrs = getContext().obtainStyledAttributes(attributeSet, R.styleable.SwipeCardView);

        try {
            mAnimationDuration =
                    attrs.getInteger(R.styleable.SwipeCardView_animation_duration,
                            DEFAULT_ANIMATION_DURATION);
            mNumberOfVisibleViews = attrs.getInteger(R.styleable.SwipeCardView_number_of_visible_view, DEFAULT_VISIBLE_CARD);
            mViewSpacing = attrs.getInteger(R.styleable.SwipeCardView_view_spacing, DEFAULT_VIEW_SPACING);
            mViewRotation = attrs.getInteger(R.styleable.SwipeCardView_view_rotation, DEFAULT_CARD_ROTATION);
        } finally {
            attrs.recycle();
        }
    }

    private void initialize() {
        mRandom = new Random();

        setClipToPadding(false);
        setClipChildren(false);

        mSwipeRotation = DEFAULT_SWIPE_ROTATION;
        mScaleFactor = DEFAULT_SCALE_FACTOR;
        mDisableHwAcceleration = DEFAULT_DISABLE_HW_ACCELERATION;

        mSwipeHelper = new SwipeHelper(this);
        mSwipeHelper.setAnimationDuration(mAnimationDuration);
        mSwipeHelper.setRotation(mSwipeRotation);


        mDataObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                invalidate();
                requestLayout();
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        if (mAdapter == null || mAdapter.isEmpty()) {
            mCurrentViewIndex = 0;
            removeAllViewsInLayout();
            return;
        }

        for (int x = getChildCount();
             x < mNumberOfVisibleViews && mCurrentViewIndex + x < mAdapter.getCount();
             x++) {
            addView(x + mCurrentViewIndex, true);
        }

        reorderItems();
    }


    private void addPreviousView() {
        Log.d(TAG, "addPreviousView");
        if (!isFirstCardDisplayed()) {
            if (getChildCount() == mNumberOfVisibleViews) {
                View lastCard = getChildAt(0);
                removeCardView(lastCard);
            }
            View topCard = addView(--mCurrentViewIndex, false);
            topCard.setX(-getWidth() + topCard.getX());
            topCard.setRotation(-mSwipeRotation);

            reorderItems();
        }
    }

    private View addView(int index, boolean isToLast) {
        Log.d(TAG, "Add card at index: " + index);
        View cardView = mAdapter.getView(index, null, this);

        if (!mDisableHwAcceleration) {
            cardView.setLayerType(LAYER_TYPE_HARDWARE, null);
        }

        int width = getWidth() - (getPaddingLeft() + getPaddingRight());
        int height = getHeight() - (getPaddingTop() + getPaddingBottom());

        LayoutParams params = cardView.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
        }

        int measureSpecWidth = MeasureSpec.AT_MOST;
        int measureSpecHeight = MeasureSpec.AT_MOST;

        if (params.width == LayoutParams.MATCH_PARENT) {
            measureSpecWidth = MeasureSpec.EXACTLY;
        }

        if (params.height == LayoutParams.MATCH_PARENT) {
            measureSpecHeight = MeasureSpec.EXACTLY;
        }

        cardView.measure(measureSpecWidth | width, measureSpecHeight | height);
        addViewInLayout(cardView, isToLast ? 0 : -1, params, true);


        return cardView;
    }


    private void reorderItems() {
        for (int x = 0; x < getChildCount(); x++) {
            View childView = getChildAt(x);
            int topViewIndex = getChildCount() - 1;

            int distanceToViewAbove = (topViewIndex * mViewSpacing) - (x * mViewSpacing);
            int newPositionX = (getWidth() - childView.getMeasuredWidth()) / 2;
            int newPositionY = distanceToViewAbove + getPaddingTop();

            childView.layout(
                    newPositionX,
                    getPaddingTop(),
                    newPositionX + childView.getMeasuredWidth(),
                    getPaddingTop() + childView.getMeasuredHeight());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                childView.setTranslationZ(x);
            }

            float scaleFactor = (float) Math.pow(mScaleFactor, getChildCount() - x);

            if (x == topViewIndex) {
                mSwipeHelper.unregisterObservedView();
                mTopView = childView;
                mTopView.animate().rotation(0).setDuration(mAnimationDuration);
                mSwipeHelper.registerObservedView(mTopView);
            } else {
                childView.animate().rotation(mRandom.nextInt(mViewRotation) - (mViewRotation / 2)).setDuration(mAnimationDuration);
            }
            childView.animate()
                    .x(newPositionX)
                    .y(newPositionY)
                    .scaleX(scaleFactor)
                    .scaleY(scaleFactor)
                    .alpha(1)
                    .setDuration(mAnimationDuration);
        }
    }

    private void removeTopView() {
        removeCardView(mTopView);
    }

    private void removeCardView(View cardView) {
        if (cardView != null) {
            removeView(cardView);
            cardView = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void onViewSwipedToLeft() {
        mCurrentViewIndex++;
        Log.d(TAG, "onViewSwipedToLeft");
        removeTopView();
    }

    public void onViewSwipedToRight() {
        addPreviousView();
        Log.d(TAG, "onViewSwipedToRight");
    }

    public void setCurrentViewIndex(int index) {
        this.mCurrentViewIndex = index;
    }

    public void onTouchCardView() {
        FlipAnimation flipAnimation = new FlipAnimation(mTopView, mTopView);


        if (mTopView.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        mTopView.startAnimation(flipAnimation);

        flipAnimation.setListener(new FlipAnimation.TransitionEventListener() {
            @Override
            public void onTransition(View v) {
                SwipeCardAdapter.CardViewHolder holder = (SwipeCardAdapter.CardViewHolder) v.getTag();
                holder.switchView();
            }
        });
    }


    /**
     * Check when top card view is the last card.
     *
     * @return True if last card is top of viewgroup, otherwise false.
     */
    public boolean isLastCardDisplayed() {
        if (mCurrentViewIndex == mAdapter.getCount() - 1) {
            return true;
        }
        return false;
    }

    /**
     * Check when top card view is the first card.
     *
     * @return True if first card is top of viewgroup, otherwise false.
     */
    public boolean isFirstCardDisplayed() {
        if (mCurrentViewIndex == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the adapter currently in use in this SwipeStack.
     *
     * @return The adapter currently used to display data in this SwipeStack.
     */
    public Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * Sets the data behind this SwipeView.
     *
     * @param adapter The Adapter which is responsible for maintaining the
     *                data backing this list and for producing a view to represent an
     *                item in that data set.
     * @see #getAdapter()
     */
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) mAdapter.unregisterDataSetObserver(mDataObserver);
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(mDataObserver);
    }

}
