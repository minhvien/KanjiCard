package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmvien on 5/9/17.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonSelectorViewHolder> {
    private final static String TAG = LessonAdapter.class.getSimpleName();

    private Context context;
    private List<Lessons> lessonsList;


    public LessonAdapter(Context context, List<Lessons> lessonsList) {
        this.context = context;
        this.lessonsList = lessonsList;
    }

    @Override
    public LessonSelectorViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
        LessonSelectorViewHolder viewHolder = new LessonSelectorViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LessonSelectorViewHolder holder, int position) {
        Lessons lesson = (Lessons) this.lessonsList.get(position);
        holder.title.setText(lesson.getTitle());
        List<String> description = getDescription(lesson);
        String subTitle1 = TextUtils.join(",", description);
        String subTitle2 = "";
        int splitPoint = 5;
        if (description.size() > splitPoint) {
            List<String> description1 = description.subList(0, splitPoint);
            List<String> description2 = description.subList(splitPoint, description.size());
            subTitle1 = TextUtils.join(",", description1);
            subTitle2 = TextUtils.join(",", description2);
        }
        holder.subtitle1.setText(subTitle1);
        holder.subtitle2.setText(subTitle2);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lessonsList.size();
    }

    private List<String> getDescription(Lessons lesson) {
        String description = "";
        List<String> sampleWords = new ArrayList<>();
        if (lesson.getWords() != null) {
            for (Word word : lesson.getWords()) {
                sampleWords.add(word.getKanji());
            }
        }
        return sampleWords;
    }
}
