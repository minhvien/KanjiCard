package com.noname.tmvien.kanjicards.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by tmvien on 6/29/17.
 */

public class KanjiFragment extends Fragment implements ScreenShotable{

    public static KanjiFragment newInstance(int resId) {
        KanjiFragment kanjiFragment = new KanjiFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        kanjiFragment.setArguments(bundle);
        return kanjiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kanji_fragment, container, false);
        return view;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
