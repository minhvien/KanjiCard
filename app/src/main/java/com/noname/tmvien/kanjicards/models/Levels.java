package com.noname.tmvien.kanjicards.models;

import com.google.android.gms.internal.zzbmj;
import com.google.android.gms.internal.zzbml;
import com.google.android.gms.internal.zzboz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 4/25/17.
 */
@IgnoreExtraProperties
public class Levels implements Serializable {

    private int jlpt;
    private List<Lessons> lessions;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Lessons> getLessions() {
        return lessions;
    }

    public void setLessions(List<Lessons> lessions) {
        this.lessions = lessions;
    }

    public int getJlpt() {
        return jlpt;
    }

    public void setJlpt(int jlpt) {
        this.jlpt = jlpt;
    }


}

