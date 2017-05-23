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

//    @Exclude
//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("jlpt", jlpt);
//        result.put("lessions", lessions);
//        return result;
//    }

    public String getDescriptionWithContext(Context context) {
        String description = "";
        int totalWords = 0;
        int totalLesson = 0;
        if (this.lessions != null) {
            for (Lessons lesson : this.lessions) {
                totalWords += lesson.getWords().size();
            }
            totalLesson = this.lessions.size();
        }
        Resources res = context.getResources();
        description = String.format(res.getString(R.string.level_cells_subtitle), totalLesson, totalWords);
        return description;
    }
}

