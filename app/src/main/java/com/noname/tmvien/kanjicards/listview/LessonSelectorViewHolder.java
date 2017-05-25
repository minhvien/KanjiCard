package com.noname.tmvien.kanjicards.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;

/**
 * Created by tmvien on 5/9/17.
 */
public class LessonSelectorViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView subtitle;

    public LessonSelectorViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.subtitle = (TextView) view.findViewById(R.id.subtitle);
    }
}
