package com.noname.tmvien.kanjicards.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 4/25/17.
 */
@IgnoreExtraProperties
public class Levels implements Serializable{

    private int jlpt;
    private List<Lessons> lessons;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }

    public int getJlpt() {
        return jlpt;
    }

    public void setJlpt(int jlpt) {
        this.jlpt = jlpt;
    }
}

