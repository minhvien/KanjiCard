package com.noname.tmvien.kanjicards.models;

import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.database.IgnoreExtraProperties;
import com.noname.tmvien.kanjicards.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 4/25/17.
 */
@IgnoreExtraProperties
public class Levels implements Serializable{

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

