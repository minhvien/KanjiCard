package com.noname.tmvien.kanjicards.listview;

import android.view.View;

/**
 * Created by nhkha on 5/23/17.
 */

public interface ItemClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
