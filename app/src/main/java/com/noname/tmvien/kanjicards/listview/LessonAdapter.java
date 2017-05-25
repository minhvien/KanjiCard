package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Lessons;

import java.util.List;

/**
 * Created by tmvien on 5/9/17.
 */

public class LessonAdapter extends ModelAdapter {
    private final static String TAG = LessonAdapter.class.getSimpleName();

    public LessonAdapter(Context context, List modelTypes) {
        super(context, modelTypes);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return super.getView(i, view, viewGroup);
    }
}
