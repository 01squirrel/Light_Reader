package com.example.qmread.bean;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class BookListData {
    private String novelUrl;
    private String name;
    private String cover;
    private int chapterNum;
    private int chapterIndex;
    private int position;
    private String type;
    private int secondPosition;

    public BookListData(String novelUrl, String name, String cover, int chapterNum, int chapterIndex, int position, String type, int secondPosition) {
        this.novelUrl = novelUrl;
        this.name = name;
        this.cover = cover;
        this.chapterNum = chapterNum;
        this.chapterIndex = chapterIndex;
        this.position = position;
        this.type = type;
        this.secondPosition = secondPosition;
    }

    public String getNovelUrl() {
        return novelUrl;
    }

    public void setNovelUrl(String novelUrl) {
        this.novelUrl = novelUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(int chapterNum) {
        this.chapterNum = chapterNum;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(int secondPosition) {
        this.secondPosition = secondPosition;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "BookListData{" +
                "novelUrl='" + novelUrl + '\'' +
                ",name='" + name + '\'' +
                ",cover='" + cover + '\'' +
                ",chapterNum" + chapterNum +
                ",chapterIndex=" + chapterIndex +
                ",position=" + position +
                ",type=" + type +
                ",secondPosition" + secondPosition +
                '}';
    }
}
