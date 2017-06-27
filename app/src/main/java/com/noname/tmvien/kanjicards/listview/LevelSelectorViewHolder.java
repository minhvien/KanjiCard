package com.noname.tmvien.kanjicards.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;

/**
 * Created by tmvien on 5/8/17.
 */
public class LevelSelectorViewHolder extends RecyclerView.ViewHolder  {
    TextView title;
    public LevelSelectorViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
    }
}

