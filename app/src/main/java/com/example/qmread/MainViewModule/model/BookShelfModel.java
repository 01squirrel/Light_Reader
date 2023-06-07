package com.example.qmread.MainViewModule.model;

import com.example.qmread.MainViewModule.contract.BookShelfContract;

public class BookShelfModel implements BookShelfContract.Model {
    private static final String TAG = "BookshelfModel";
    private BookShelfContract.Presenter Presenter;


    @Override
    public void queryAllBook() {
        new Thread(() -> {
            Object mDbManager = null;
           // final List<BookListData> dataList = mDbManager.queryAllBookshelfNovel();
            //new Handler(Looper.getMainLooper()).post(() -> Presenter.queryAllBookSuccess(dataList));
        }).start();
    }

    @Override
    public void unZipEpub(String filePath) {

    }
}
