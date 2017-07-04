package com.noname.tmvien.kanjicards.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.SwipeCardAdapter;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Word;
import com.noname.tmvien.kanjicards.view.SwipeCardView;

import java.util.List;

/**
 * Created by tmvien on 7/3/17.
 */

public class WordCardActivity extends AppCompatActivity {

    private SwipeCardView mSwipeCard;
    private List<Word> words;
    private SwipeCardAdapter mSwipeCardAdapter;
    private String idLevel;
    private String idLesson;
    private Lessons lesson;
    private int iCurrentWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(this.getResources().getDrawable(R.color.salmon));
        bar.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.salmon));
        }
        mSwipeCard = (SwipeCardView) findViewById(R.id.swipeCard);

        Intent intent = getIntent();
        if (intent != null) {
            idLevel = intent.getStringExtra("idLevel");
            idLesson = intent.getStringExtra("idLesson");
            lesson = (Lessons) intent.getSerializableExtra("Lesson");
            iCurrentWord = intent.getIntExtra("iCurrentWord", 0);
            this.words = lesson.getWords();
            mSwipeCardAdapter = new SwipeCardAdapter(getApplicationContext(), words);
            mSwipeCard.setAdapter(mSwipeCardAdapter);
            mSwipeCard.setCurrentViewIndex(iCurrentWord);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
