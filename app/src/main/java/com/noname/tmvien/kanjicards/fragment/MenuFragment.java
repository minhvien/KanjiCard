package com.noname.tmvien.kanjicards.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noname.tmvien.kanjicards.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by tmvien on 6/28/17.
 */

public class MenuFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String KANJI = "kanji";
    public static final String QUIZ = "quiz";
    public static final String SETTING = "setting";
    public static final String RATE = "rate";
    public static final String ABOUT = "about";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    public static MenuFragment newInstance(int resId) {
        MenuFragment contentFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                MenuFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

