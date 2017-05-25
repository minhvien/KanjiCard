package com.noname.tmvien.kanjicards.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;

/**
 * Created by tmvien on 5/8/17.
 */
public class LevelSelectorViewHolder extends RecyclerView.ViewHolder  {
    ImageView image;
    TextView title;
    TextView subtitle;
    public LevelSelectorViewHolder(View view) {
        super(view);
        this.image = (ImageView) view.findViewById(R.id.image);
        this.title = (TextView) view.findViewById(R.id.title);
        this.subtitle = (TextView) view.findViewById(R.id.subtitle);
    }
}

