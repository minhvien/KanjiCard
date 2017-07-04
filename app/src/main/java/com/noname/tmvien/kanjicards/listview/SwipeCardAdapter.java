package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.noname.tmvien.kanjicards.R;

import java.util.List;

/**
 * Created by tmvien on 7/3/17.
 */

public class SwipeCardAdapter extends BaseAdapter {
    private List<String> mData;
    private Context mContext;

    public SwipeCardAdapter(Context context, List<String> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
        }
        return convertView;
    }
}
