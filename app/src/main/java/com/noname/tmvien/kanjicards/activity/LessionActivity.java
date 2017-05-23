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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
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
        novalueTextView = (TextView) findViewById(R.id.novalueTextView);

        Intent intent = getIntent();
        if(intent != null){
            level = (Levels) intent.getSerializableExtra("level");
        }

        if(level != null && level.getLessions() != null && level.getLessions().size() > 0) {
            lessonList = new ArrayList<>();
            recyclerAdapter = new LessonAdapter(getApplicationContext(), lessonList);
            recyclerView.setAdapter(recyclerAdapter);

            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseDatabase.getReference("levels").child(level.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lessonList.clear();
                    Levels level = dataSnapshot.getValue(Levels.class);
                    recyclerAdapter = new LessonAdapter(getApplicationContext(), level.getLessions());
                    recyclerView.setAdapter(recyclerAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            novalueTextView.setVisibility(View.VISIBLE);
        }
        setTitle(getString(R.string.lesson_list_navigation_title));
    }

}
