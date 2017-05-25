package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Levels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LessionActivity extends AppCompatActivity {
    private final static String TAG = LessionActivity.class.getSimpleName();

    private List<Lessons> lessonList;

    private TextView novalueTextView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private Levels level;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lession_activity);

        recyclerView = (RecyclerView) findViewById(R.id.lessonListRecycler);
        novalueTextView = (TextView) findViewById(R.id.novalueTextView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent intent = new Intent(LessionActivity.this, WordListActivity.class);
                intent.putExtra("idLevel", level.getId());
                intent.putExtra("idLesson", lessonList.get(position).getId());
                intent.putExtra("Lesson",  (Serializable) lessonList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Intent intent = getIntent();
        if (intent != null) {
            level = (Levels) intent.getSerializableExtra("level");

            if (level != null && level.getLessions() != null && level.getLessions().size() > 0) {
                lessonList = new ArrayList<>();
                recyclerAdapter = new LessonAdapter(getApplicationContext(), lessonList);
                recyclerView.setAdapter(recyclerAdapter);

                mFirebaseDatabase = FirebaseDatabase.getInstance();

                mFirebaseDatabase.getReference("levels").child(level.getId()).child("lessions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lessonList.clear();
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            try {
                                Lessons lesson = item.getValue(Lessons.class);
                                if (lesson == null) {
                                    return;
                                }
                                lesson.setId(item.getKey());
                                lessonList.add(lesson);
                                level.setLessions(lessonList);
                                recyclerAdapter.notifyDataSetChanged();
                            } catch (DatabaseException ex) {
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        novalueTextView.setVisibility(View.VISIBLE);
                    }
                });

            } else {
                novalueTextView.setVisibility(View.VISIBLE);
            }
        }
        setScreenTitle();
    }

    private void setScreenTitle() {
        if (level != null) {
            setTitle("Level " + level.getJlpt());
        } else {
            setTitle(R.string.lesson_list_navigation_title);
        }
    }
}
