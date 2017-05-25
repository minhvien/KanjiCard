package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Levels;

import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LevelAdapter extends ModelAdapter {
    private final static String TAG = LevelAdapter.class.getSimpleName();


    public LevelAdapter(Context context, List<Levels> modelTypes) {
        super(context, modelTypes);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LevelSelectorViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.level_item, null);
            viewHolder = new LevelSelectorViewHolder();
            viewHolder.levelIconImage = (ImageView) view.findViewById(R.id.levelIconImage);
            viewHolder.levelTextView = (TextView) view.findViewById(R.id.levelTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LevelSelectorViewHolder) view.getTag();
        }

        Levels level = (Levels) modelTypes.get(i);
        viewHolder.levelTextView.setText("JLPT " + level.getJlpt());

        return view;
    }
}
