package com.noname.tmvien.kanjicards;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tmvien on 6/30/17.
 */

public class KanjiCardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
