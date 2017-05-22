package com.noname.tmvien.kanjicards.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 5/8/17.
 */
@IgnoreExtraProperties
public class Lessons implements Serializable{

    private int order;
    private String title;
    private List<Word> words;
    private String id;

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

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> wordList) {
        this.words = wordList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
