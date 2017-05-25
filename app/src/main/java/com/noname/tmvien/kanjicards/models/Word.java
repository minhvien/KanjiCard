package com.noname.tmvien.kanjicards.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmvien on 5/22/17.
 */
@IgnoreExtraProperties
public class Word implements Serializable {

    private String id;
    private String components;
    private List<Word> example;
    private String han;
    private String jlpt;
    private String kanji;
    private List<String> kun;
    private List<String> mean;
    private List<String> on;
    private int order;
    private String sortMean;
    private String strokes;
    private String hiragana;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public List<Word> getExample() {
        return example;
    }

    public void setExample(List<Word> example) {
        this.example = example;
    }

    public String getHan() {
        return han;
    }

    public void setHan(String han) {
        this.han = han;
    }

    public String getJlpt() {
        return jlpt;
    }

    public void setJlpt(String jlpt) {
        this.jlpt = jlpt;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public List<String> getKun() {
        return kun;
    }

    public void setKun(List<String> kun) {
        this.kun = kun;
    }

    public List<String> getMean() {
        return mean;
    }

    public void setMean(List<String> mean) {
        this.mean = mean;
    }

    public List<String> getOn() {
        return on;
    }

    public void setOn(List<String> on) {
        this.on = on;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSortMean() {
        return sortMean;
    }

    public void setSortMean(String sortMean) {
        this.sortMean = sortMean;
    }

    public String getStrokes() {
        return strokes;
    }

    public void setStrokes(String strokes) {
        this.strokes = strokes;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }
}
