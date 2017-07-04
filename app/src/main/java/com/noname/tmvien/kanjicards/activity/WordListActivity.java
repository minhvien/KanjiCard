package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
import com.noname.tmvien.kanjicards.listview.WordListAdapter;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmvien on 5/23/17.
 */

public class WordListActivity extends AppCompatActivity {
    private static final String TAG = WordListActivity.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;
    private String idLevel;
    private String idLesson;
    private Lessons lesson;
    private List<Word> words;

    private TextView novalueTextView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(this.getResources().getDrawable(R.color.fern));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.fern));
        }
        bar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.word_activity);
        recyclerView = (RecyclerView) findViewById(R.id.wordListRecycler);
        novalueTextView = (TextView) findViewById(R.id.novalueTextView);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (position < words.size()) {
                    Intent intent = new Intent(WordListActivity.this, WordCardActivity.class);
                    intent.putExtra("Word", (Serializable) words.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Intent intent = getIntent();
        if (intent != null) {
            idLevel = intent.getStringExtra("idLevel");
            idLesson = intent.getStringExtra("idLesson");
            lesson = (Lessons) intent.getSerializableExtra("Lesson");
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            if (!idLesson.isEmpty() && !idLevel.isEmpty()) {
                words = new ArrayList<>();
                recyclerAdapter = new WordListAdapter(getApplicationContext(), words);
                recyclerView.setAdapter(recyclerAdapter);
                mFirebaseDatabase.getReference("levels").child(idLevel).child("lessons").child(idLesson).child("words").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        words.clear();
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            try {
                                Word word = item.getValue(Word.class);
                                if (word == null) {
                                    android.util.Log.d(TAG, "Level is null");
                                    return;
                                }
                                word.setId(item.getKey());
                                words.add(word);
                                recyclerAdapter.notifyDataSetChanged();
                            } catch (DatabaseException ex) {
                                android.util.Log.e(TAG, "error: " + ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                novalueTextView.setVisibility(View.VISIBLE);
            }
        }
        setScreenTitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void setScreenTitle() {
        if (lesson != null && lesson.getTitle() != null) {
            setTitle(lesson.getTitle());
        } else {
            setTitle(R.string.word_list_navigation_title);
        }
    }
}
