package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Lessons;

import java.util.List;

/**
 * Created by tmvien on 5/9/17.
 */

public class LessonAdapter extends BaseAdapter {
    private final static String TAG = LessonAdapter.class.getSimpleName();

    private Context context;
    private List<Lessons> lessonsList;


    public LessonAdapter(Context context, List<Lessons> lessonsList){
        this.context = context;
        this.lessonsList = lessonsList;
    }

    @Override
    public int getCount() {
        return lessonsList.size();
    }

    @Override
    public Object getItem(int i) {
        return lessonsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LessonSelectorViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.lesson_item, null);
            viewHolder = new LessonSelectorViewHolder();
            viewHolder.lessionTextView = (TextView) view.findViewById(R.id.lessonTextView);
            viewHolder.wordsTextView = (TextView) view.findViewById(R.id.wordsTextView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LessonSelectorViewHolder) view.getTag();
        }

        Lessons lesson = lessonsList.get(i);
        viewHolder.lessionTextView.setText(lesson.getTitle());

        return view;
    }
}
