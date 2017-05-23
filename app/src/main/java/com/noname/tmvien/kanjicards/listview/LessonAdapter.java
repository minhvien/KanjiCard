package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Lessons;

import java.util.List;

/**
 * Created by tmvien on 5/9/17.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonSelectorViewHolder> {
    private final static String TAG = LessonAdapter.class.getSimpleName();

    private Context context;
    private List<Lessons> lessonsList;


    public LessonAdapter(Context context, List<Lessons> lessonsList){
        this.context = context;
        this.lessonsList = lessonsList;
    }

    @Override
    public LessonSelectorViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, null);
        LessonSelectorViewHolder viewHolder = new LessonSelectorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LessonSelectorViewHolder holder, int position) {
        Lessons lesson = (Lessons) this.lessonsList.get(position);
        holder.title.setText(lesson.getTitle());
        holder.subtitle.setText(lesson.getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lessonsList.size();
    }
}
