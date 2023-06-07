package com.example.qmread.MainViewModule.presenter;

import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.BookListData;
import com.example.qmread.MainViewModule.contract.BookShelfContract;
import com.example.qmread.MainViewModule.model.BookShelfModel;
import com.example.qmread.Utils.entity.epub.OpfData;
import com.example.qmread.base.BasePresenter;

import java.util.List;

public class BookShelfPresenter extends BasePresenter<BookShelfContract.View>
implements BookShelfContract.Presenter{

private final BookShelfContract.Model bookModel;
public  BookShelfPresenter(){bookModel = new BookShelfModel();
}
    @Override
    public void queryAllBookSuccess(List<BookEntity> dataList) {
        if (isAttachView()) {
            getMvpView().queryAllBookSuccess(dataList);
        }
    }

    @Override
    public void queryAllBookError(String errorMsg) {
        if (isAttachView()) {
            getMvpView().queryAllBookError(errorMsg);
        }
    }

    @Override
    public void unZipEpubSuccess(String filePath, OpfData opfData) {
        if (isAttachView()) {
            getMvpView().unZipEpubSuccess(filePath, opfData);
        }
    }

    @Override
    public void unZipEpubError(String errorMsg) {
        if (isAttachView()) {
            getMvpView().unZipEpubError(errorMsg);
        }
    }

    @Override
    public void queryAllBook() {
        bookModel.queryAllBook();
    }

    @Override
    public void unZipEpub(String filePath) {
        bookModel.unZipEpub(filePath);
    }
}
