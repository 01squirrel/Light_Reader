package com.example.qmread.Utils.entity;

public class Book {
    private Long id;
    private String novelUrl;
    private String name;
    private String cover;

    public Book(Long id, String novelUrl, String name, String cover) {
        this.id = id;
        this.novelUrl = novelUrl;
        this.name = name;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
