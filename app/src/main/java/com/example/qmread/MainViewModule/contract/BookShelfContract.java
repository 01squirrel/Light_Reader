package com.example.qmread.MainViewModule.contract;

import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.BookListData;
import com.example.qmread.Utils.entity.epub.OpfData;

import java.util.List;

public interface BookShelfContract {
    interface View {
        void queryAllBookSuccess(List<BookEntity> dataList);
        void queryAllBookError(String errorMsg);
        void unZipEpubSuccess(String filePath, OpfData opfData);
        void unZipEpubError(String errorMsg);
    }
    interface Presenter {
        void queryAllBookSuccess(List<BookEntity> dataList);
        void queryAllBookError(String errorMsg);
        void unZipEpubSuccess(String filePath, OpfData opfData);
        void unZipEpubError(String errorMsg);

        void queryAllBook();
        void unZipEpub(String filePath);
    }
    interface Model {
        void queryAllBook();                // 从数据库中查询所有书籍信息
        void unZipEpub(String filePath);    // 解压 epub 文件
    }
}
