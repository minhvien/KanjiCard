package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Lessons;
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
        Levels level = (Levels) this.levels.get(position);
        holder.title.setText("Level " + level.getJlpt());
        holder.subtitle.setText(getDescriptionLevel(level));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return levels.size();
    }

    private String getDescriptionLevel(Levels level) {
        String description = "";
        int totalWords = 0;
        int totalLesson = 0;
        if (level != null && level.getLessons() != null) {
            for (Lessons lesson : level.getLessons()) {
                totalWords += lesson.getWords().size();
            }
            totalLesson = level.getLessons().size();
        }
        Resources res = context.getResources();
        description = String.format(res.getString(R.string.level_cells_subtitle), totalLesson, totalWords);
        return description;
    }
}

