package com.example.qmread.enums;


public enum Language {

    simplified,//简体中文
    traditional;//繁体中文


    Language() {


    }

    public static Language get(int var0) {
        return values()[var0];
    }

    public static Language fromString(String string) {
        return Language.valueOf(string);
    }
}
