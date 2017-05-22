package com.noname.tmvien.kanjicards.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by tmvien on 5/8/17.
 */
@IgnoreExtraProperties
public class Lessons implements Serializable{

    private int order;
    private String title;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
