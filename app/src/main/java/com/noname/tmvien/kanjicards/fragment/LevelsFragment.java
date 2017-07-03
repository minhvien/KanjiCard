package com.noname.tmvien.kanjicards.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.activity.LessionActivity;
import com.noname.tmvien.kanjicards.listview.ItemClickListener;
import com.noname.tmvien.kanjicards.listview.LevelAdapter;
import com.noname.tmvien.kanjicards.listview.RecyclerTouchListener;
import com.noname.tmvien.kanjicards.models.Levels;
import com.noname.tmvien.kanjicards.utils.Log;

import java.io.Serializable;
import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by tmvien on 6/29/17.
 */

public class LevelsFragment extends Fragment implements ScreenShotable{
    private static final String TAG = LevelsFragment.class.getSimpleName();

    private FirebaseDatabase mFirebaseDatabase;

    private RecyclerView recyclerView;
    private View containerView;
    private RecyclerView.Adapter recyclerAdapter;

    private ArrayList<Levels> levelList;

    private Bitmap bitmap;

    public static LevelsFragment newInstance(int resId) {
        LevelsFragment levelsFragment = new LevelsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        levelsFragment.setArguments(bundle);
        return levelsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.noname.tmvien.kanjicards.utils.Log.d(TAG, "start " + TAG);
        View view = inflater.inflate(R.layout.levels_frament, container, false);
        levelList = new ArrayList<>();

        containerView = (ViewGroup) view.findViewById(R.id.container);
        recyclerView = (RecyclerView) view.findViewById(R.id.levelListRecyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (position < levelList.size()) {
                    Intent intent = new Intent(getActivity(), LessionActivity.class);
                    intent.putExtra("level", (Serializable) levelList.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerAdapter = new LevelAdapter(getActivity(), levelList);
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



        getActivity().setTitle(getString(R.string.level_list_navigation_title));
        return view;
    }

    @Override
    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                LevelsFragment.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
