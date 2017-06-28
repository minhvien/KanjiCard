package com.noname.tmvien.kanjicards.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.LevelAdapter;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
import com.noname.tmvien.kanjicards.models.Levels;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private ArrayList<Levels> levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(this.getResources().getDrawable(R.color.sky));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.sky));
        }

        setContentView(R.layout.activity_main);
        levelList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.levelListRecyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (position < levelList.size()) {
                    Intent intent = new Intent(MainActivity.this, LessionActivity.class);
                    intent.putExtra("level", (Serializable) levelList.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerAdapter = new LevelAdapter(getApplicationContext(), levelList);
        recyclerView.setAdapter(recyclerAdapter);

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
                        recyclerAdapter.notifyDataSetChanged();
                    } catch (DatabaseException ex) {
                        Log.e(TAG, "error: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setTitle(getString(R.string.level_list_navigation_title));

    }
}
