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
    private List<Lessons> lessions;

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

//    @Exclude
//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("jlpt", jlpt);
//        result.put("lessions", lessions);
//        return result;
//    }
}

