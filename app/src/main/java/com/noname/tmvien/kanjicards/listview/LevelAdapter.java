package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Levels;

import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LevelAdapter extends BaseAdapter{

    private final static String TAG = LevelAdapter.class.getSimpleName();

    private Context context;
    private List<Levels> levels;


    public LevelAdapter (Context context, List<Levels> levels){
        this.context = context;
        this.levels = levels;
    }
    @Override
    public int getCount() {
        return levels.size();
    }

    @Override
    public Object getItem(int i) {
        return levels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LevelSelectorViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.level_item, null);
            viewHolder = new LevelSelectorViewHolder();
            viewHolder.levelIconImage = (ImageView) view.findViewById(R.id.levelIconImage);
            viewHolder.levelTextView = (TextView) view.findViewById(R.id.levelTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LevelSelectorViewHolder) view.getTag();
        }

        Levels level = levels.get(i);
        viewHolder.levelTextView.setText("JLPT " + level.getJlpt());

        return view;
    }
}
