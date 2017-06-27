package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Levels;

import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelSelectorViewHolder> {
    private final static String TAG = LevelAdapter.class.getSimpleName();

    private Context context;
    private List<Levels> levels;


    public LevelAdapter(Context context, List<Levels> levels) {
        this.context = context;
        this.levels = levels;
    }

    @Override
    public LevelSelectorViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item, null);
        LevelSelectorViewHolder viewHolder = new LevelSelectorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LevelSelectorViewHolder holder, int position) {
        if (position < levels.size()) {
            Levels level = (Levels) this.levels.get(position);
            holder.title.setText("N  " + level.getJlpt());
        } else {
            holder.title.setText("");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return levels.size() + (levels.size() % 2);
    }

}

