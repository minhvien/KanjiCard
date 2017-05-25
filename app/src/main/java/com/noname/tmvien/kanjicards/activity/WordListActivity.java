package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.models.Word;

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
    private ListView wordListView;
    private List<Word> words;
    private LessonAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.word_activity);
        wordListView = (ListView) findViewById(R.id.word_listView);

        words = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            idLevel = intent.getStringExtra("idLevel");
            idLesson = intent.getStringExtra("idLesson");
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        if (!idLesson.isEmpty() && !idLevel.isEmpty()) {
            mFirebaseDatabase.getReference("levels").child(idLevel).child("lessions").child(idLesson).child("words").addValueEventListener(new ValueEventListener() {

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

                        } catch (DatabaseException ex) {
                            android.util.Log.e(TAG, "error: " + ex.getMessage());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
