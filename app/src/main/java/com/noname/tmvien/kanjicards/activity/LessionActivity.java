package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.LessonAdapter;
import com.noname.tmvien.kanjicards.models.Lessons;
import com.noname.tmvien.kanjicards.utils.Log;

import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */

public class LessionActivity extends AppCompatActivity {
    private final static String TAG = LessionActivity.class.getSimpleName();

    private List<Lessons> lessons;
    private ListView lessonListView;
    private TextView novalueTextView;
    private LessonAdapter lessonAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lession_activity);

        lessonListView = (ListView) findViewById(R.id.lession_list);
        novalueTextView = (TextView) findViewById(R.id.novalueTextView);

        Intent intent = getIntent();
        if(intent != null){
            lessons = (List<Lessons>) intent.getSerializableExtra("lession");
        }

        if(lessons != null && lessons.size() != 0){
            lessonAdapter = new LessonAdapter(getApplicationContext(), lessons);
            lessonListView.setAdapter(lessonAdapter);
        } else {
            novalueTextView.setVisibility(View.VISIBLE);
        }
    }

}
