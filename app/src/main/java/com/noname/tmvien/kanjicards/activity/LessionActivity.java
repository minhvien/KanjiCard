package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.listview.LevelAdapter;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.models.Levels;
import com.noname.tmvien.kanjicards.utils.Log;

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
        if(intent != null){
            level = (Levels) intent.getSerializableExtra("level");
        }

        if(level != null) {
            lessonList = new ArrayList<>();
            lessonAdapter = new LessonAdapter(getApplicationContext(), lessonList);
            lessonListView.setAdapter(lessonAdapter);

            mFirebaseDatabase = FirebaseDatabase.getInstance();

            mFirebaseDatabase.getReference("levels").child(level.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lessonList.clear();
                    Levels level = dataSnapshot.getValue(Levels.class);
                    lessonAdapter = new LessonAdapter(getApplicationContext(), level.getLessions());
                    lessonListView.setAdapter(lessonAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
