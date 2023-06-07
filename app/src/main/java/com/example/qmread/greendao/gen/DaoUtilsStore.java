package com.example.qmread.greendao.gen;

import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.BookmarkEntity;
import com.example.qmread.bean.ChapterEntity;
import com.example.qmread.bean.CommentsEntity;
import com.example.qmread.bean.OrdinaryUserEntity;
import com.example.qmread.bean.ReadingStatusEntity;
import com.example.qmread.bean.RegisteredUserEntity;

/**
 * 初始化、存放及获取DaoUtils
 */
public class DaoUtilsStore {
    private static DaoUtilsStore instance;
    private final CommonDaoUtils<BookEntity> bookDaoUtils;
    private final CommonDaoUtils<RegisteredUserEntity> registeredUserUtils;
    private final CommonDaoUtils<OrdinaryUserEntity> ordinaryUserDaoUtils;
    private final CommonDaoUtils<ChapterEntity> chapterDaoUtils;
    private final CommonDaoUtils<BookmarkEntity> bookmarkDaoUtils;
    private final CommonDaoUtils<ReadingStatusEntity> readingStatusDaoUtils;
    private final CommonDaoUtils<CommentsEntity> commonDaoUtils;

    public static DaoUtilsStore getInstance() {
        if(instance == null){
            instance = new DaoUtilsStore();
        }
        return instance;
    }

    public DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();

        BookEntityDao bookEntityDao = mManager.getDaoSession().getBookEntityDao();
        bookDaoUtils = new CommonDaoUtils<>(BookEntity.class, bookEntityDao);

        RegisteredUserEntityDao registeredUserDao = mManager.getDaoSession().getRegisteredUserEntityDao();
        registeredUserUtils = new CommonDaoUtils<>(RegisteredUserEntity.class,registeredUserDao);

        OrdinaryUserEntityDao ordinaryUserDao = mManager.getDaoSession().getOrdinaryUserEntityDao();
        ordinaryUserDaoUtils = new CommonDaoUtils<>(OrdinaryUserEntity.class,ordinaryUserDao);

        ChapterEntityDao chapterEntityDao = mManager.getDaoSession().getChapterEntityDao();
        chapterDaoUtils = new CommonDaoUtils<>(ChapterEntity.class,chapterEntityDao);

        BookmarkEntityDao bookmarkEntityDao = mManager.getDaoSession().getBookmarkEntityDao();
        bookmarkDaoUtils = new CommonDaoUtils<>(BookmarkEntity.class,bookmarkEntityDao);

        ReadingStatusEntityDao readingStatusEntityDao = mManager.getDaoSession().getReadingStatusEntityDao();
        readingStatusDaoUtils = new CommonDaoUtils<>(ReadingStatusEntity.class,readingStatusEntityDao);

        CommentsEntityDao commentsEntityDao = mManager.getDaoSession().getCommentsEntityDao();
        commonDaoUtils = new CommonDaoUtils<>(CommentsEntity.class,commentsEntityDao);
    }

    public CommonDaoUtils<BookEntity> getUserDaoUtils() {
        return bookDaoUtils;
    }
    public CommonDaoUtils<RegisteredUserEntity> getRegisteredUserUtils(){return registeredUserUtils;}
    public CommonDaoUtils<OrdinaryUserEntity> getOrdinaryUserDaoUtils(){
        return ordinaryUserDaoUtils;
    }
    public CommonDaoUtils<ChapterEntity> getChapterDaoUtils(){
        return chapterDaoUtils;
    }
    public CommonDaoUtils<BookmarkEntity> getBookmarkDaoUtils(){
        return bookmarkDaoUtils;
    }
    public CommonDaoUtils<ReadingStatusEntity> getReadingStatusDaoUtils(){
        return readingStatusDaoUtils;
    }
    public CommonDaoUtils<CommentsEntity> getCommonDaoUtils(){
        return commonDaoUtils;
    }
}