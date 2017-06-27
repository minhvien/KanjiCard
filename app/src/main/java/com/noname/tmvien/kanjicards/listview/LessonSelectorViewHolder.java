package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.graphics.Typeface;
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

    public LessonSelectorViewHolder(View view, Context context) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.subtitle = (TextView) view.findViewById(R.id.subtitle);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/honoka-antique-maru.ttf");
        this.title.setTypeface(custom_font);
        this.subtitle.setTypeface(custom_font);
    }
}
