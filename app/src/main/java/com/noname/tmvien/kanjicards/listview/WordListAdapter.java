package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Word;

import java.util.List;

/**
 * Created by nhkha on 5/25/17.
 */

public class WordListAdapter extends RecyclerView.Adapter<LessonSelectorViewHolder> {
    private final static String TAG = LessonAdapter.class.getSimpleName();

    private Context context;
    private List<Word> wordList;


    public WordListAdapter(Context context, List<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
    }

    @Override
    public LessonSelectorViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, null);
        LessonSelectorViewHolder viewHolder = new LessonSelectorViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LessonSelectorViewHolder holder, int position) {
        Word word = (Word) this.wordList.get(position);
        holder.title.setText(word.getKanji());
        holder.subtitle1.setText(word.getShortMean());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return wordList.size();
    }
}
