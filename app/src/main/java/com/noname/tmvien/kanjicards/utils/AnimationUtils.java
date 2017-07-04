package com.noname.tmvien.kanjicards.utils;

import android.animation.Animator;

/**
 * Created by tmvien on 7/3/17.
 */

public class AnimationUtils  {
    public static abstract class AnimationEndListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
            // Do nothing
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // Do nothing
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // Do nothing
        }
    }
}
