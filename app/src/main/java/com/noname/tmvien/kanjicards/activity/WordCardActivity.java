package com.noname.tmvien.kanjicards.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.SwipeCardAdapter;
import com.noname.tmvien.kanjicards.view.SwipeCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmvien on 7/3/17.
 */

public class WordCardActivity extends AppCompatActivity {

    private SwipeCardView mSwipeCard;
    private List<String> mList;
    private SwipeCardAdapter mSwipeCardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);
        mSwipeCard = (SwipeCardView) findViewById(R.id.swipeCard);

        mList = new ArrayList<>();
        mList.add("12dfs");
        mList.add("12dfs");
        mList.add("12dfs");
        mSwipeCardAdapter = new SwipeCardAdapter(getApplicationContext(), mList);
        mSwipeCard.setAdapter(mSwipeCardAdapter);

    }
}
