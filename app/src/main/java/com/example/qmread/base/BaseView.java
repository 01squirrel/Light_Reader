package com.example.qmread.base;

import android.content.Context;

public interface BaseView {
    Context getContext();

    /**
     * @param title
     */
    void setTitle(String title);

    /**
     * @param description
     */
    void showDescription(String description);
}
