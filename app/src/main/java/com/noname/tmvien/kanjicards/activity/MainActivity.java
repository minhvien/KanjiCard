package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.LevelAdapter;
import com.noname.tmvien.kanjicards.models.Levels;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    private ListView listLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listLevel = (ListView) findViewById(R.id.list_level);

        final ArrayList<Levels> levelList = new ArrayList<>();
        final LevelAdapter adapter = new LevelAdapter(getApplicationContext(), levelList);
        listLevel.setAdapter(adapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase.getReference("levels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "data");
                levelList.clear();
                for (DataSnapshot levels : dataSnapshot.getChildren()) {
                    Log.d(TAG, "key : " + levels.getKey());
                    try {
                        Levels level = levels.getValue(Levels.class);
                        if (levels == null) {
                            Log.d(TAG, "Level is null");
                            return;
                        }
                        level.setId(levels.getKey());
                        levelList.add(level);
                        adapter.notifyDataSetChanged();
                    } catch (DatabaseException ex) {
                        Log.e(TAG, "error: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, LessionActivity.class);
                intent.putExtra("level", (Serializable) levelList.get(i));
                startActivity(intent);
            }
        });
    }
}
