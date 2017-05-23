package com.noname.tmvien.kanjicards.listview;

import android.view.View;

/**
 * Created by nhkha on 5/23/17.
 */

public interface ItemClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
