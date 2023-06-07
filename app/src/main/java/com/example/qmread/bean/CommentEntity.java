package com.example.qmread.bean;

import android.graphics.Bitmap;

public class CommentEntity {
    private Bitmap head;
    private String userName;
    private String date;
    private String content;

    public CommentEntity(Bitmap head, String userName, String date, String content) {
        this.head = head;
        this.userName = userName;
        this.date = date;
        this.content = content;
    }

    public Bitmap getHead() {
        return head;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
