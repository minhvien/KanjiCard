package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;

/**
 * Created by nhkha on 6/28/17.
 */

public class WordSelectorViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    public WordSelectorViewHolder(View view, Context context) {
        super(view);

        this.title = (TextView) view.findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/honoka-antique-maru.ttf");
        this.title.setTypeface(custom_font);
    }
}
