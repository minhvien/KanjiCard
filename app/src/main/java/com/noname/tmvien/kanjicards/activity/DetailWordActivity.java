package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
import com.noname.tmvien.kanjicards.listview.WordListAdapter;
import com.noname.tmvien.kanjicards.models.Word;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 5/26/17.
 */

public class DetailWordActivity extends AppCompatActivity {
    private static final String TAG = DetailWordActivity.class.getSimpleName();
    private Word word;
    private TextView kanjiTextView, hanTextView, kunTextView, onTextView, shortTextView, meanTextView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_word_activity);

        Intent intent = getIntent();
        if (intent != null) {
            word = (Word) intent.getSerializableExtra("Word");
        }
        if (word == null) {
            return;
        }

        kanjiTextView = (TextView) findViewById(R.id.kanji_text_view);
        hanTextView = (TextView) findViewById(R.id.han_text_view);
        kunTextView = (TextView) findViewById(R.id.kun_text_view);
        onTextView = (TextView) findViewById(R.id.on_text_view);
        shortTextView = (TextView) findViewById(R.id.short_mean_text_view);
        meanTextView = (TextView) findViewById(R.id.mean_text_view);
        recyclerView = (RecyclerView) findViewById(R.id.wordListRecycler);

        kanjiTextView.setText(word.getKanji());
        hanTextView.setText(word.getHan());
        kunTextView.setText(getStringInList(word.getKun(), false));
        onTextView.setText(getStringInList(word.getOn(), false));
        shortTextView.setText(word.getShortMean());
        meanTextView.setText(getStringInList(word.getMean(), true));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        recyclerAdapter = new WordListAdapter(getApplicationContext(), word.getExample());
        recyclerView.setAdapter(recyclerAdapter);

    }

    private String getStringInList(List<String> str, boolean isMean) {
        String result = "";
        for (String s : str
                ) {
            if (!result.isEmpty()) {
                result += isMean ? "\n" : ", ";
            }
            result += s;
        }
        return result;
    }
}
