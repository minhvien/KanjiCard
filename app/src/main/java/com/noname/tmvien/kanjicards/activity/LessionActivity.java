package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Levels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LessionActivity extends AppCompatActivity {
    private final static String TAG = LessionActivity.class.getSimpleName();

    private List<Lessons> lessonList;
    private ListView lessonListView;
    private TextView novalueTextView;
    private LessonAdapter lessonAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private Levels level;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lession_activity);

        lessonListView = (ListView) findViewById(R.id.lession_list);
        novalueTextView = (TextView) findViewById(R.id.novalueTextView);

        Intent intent = getIntent();
        if (intent != null) {
            level = (Levels) intent.getSerializableExtra("level");
        }

        if (level != null) {
            lessonList = new ArrayList<>();
            lessonAdapter = new LessonAdapter(getApplicationContext(), lessonList);
            lessonListView.setAdapter(lessonAdapter);

            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseDatabase.getReference("levels").child(level.getId()).child("lessions").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lessonList.clear();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        try {
                            Lessons lesson = item.getValue(Lessons.class);
                            if (lesson == null) {
                                Log.d(TAG, "Level is null");
                                return;
                            }
                            lesson.setId(item.getKey());
                            lessonList.add(lesson);
                            level.setLessions(lessonList);
                            lessonAdapter.notifyDataSetChanged();
                        } catch (DatabaseException ex) {
                            Log.e(TAG, "error: " + ex.getMessage());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    novalueTextView.setVisibility(View.VISIBLE);
                }
            });
        }

        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LessionActivity.this, WordListActivity.class);
                intent.putExtra("idLevel",  level.getId());
                intent.putExtra("idLesson", lessonList.get(i).getId());
                startActivity(intent);
            }
        });
    }

}
