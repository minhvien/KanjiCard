package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by tmvien on 5/25/17.
 */

public class ModelAdapter<T> extends BaseAdapter {
    protected List<T> modelTypes;
    protected Context context;


    public ModelAdapter(Context context, List<T> modelTypes) {
        this.context = context;
        this.modelTypes = modelTypes;
    }

    @Override
    public int getCount() {
        return modelTypes.size();
    }

    @Override
    public T getItem(int i) {
        return modelTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }
}
