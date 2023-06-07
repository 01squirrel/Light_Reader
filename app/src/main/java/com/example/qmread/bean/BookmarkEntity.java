package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.qmread.greendao.gen.DaoSession;
import com.example.qmread.greendao.gen.ChapterEntityDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.example.qmread.greendao.gen.BookEntityDao;
import com.example.qmread.greendao.gen.BookmarkEntityDao;

@Entity
public class BookmarkEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 5L;
    @Id(autoincrement = true)
    @Index(unique = true)
    private long bookmarkId;
    private long bookId;
    @ToOne(joinProperty = "bookId")
    private BookEntity bookEntity;
    private long chapterId;
    @ToOne(joinProperty = "bookmarkId")
    private ChapterEntity chapterEntity;
    private Date setTime;
    private Date changeTime;
    private String beginParagraph;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1754868418)
    private transient BookmarkEntityDao myDao;
    @Generated(hash = 170566442)
    public BookmarkEntity(long bookmarkId, long bookId, long chapterId,
            Date setTime, Date changeTime, String beginParagraph) {
        this.bookmarkId = bookmarkId;
        this.bookId = bookId;
        this.chapterId = chapterId;
        this.setTime = setTime;
        this.changeTime = changeTime;
        this.beginParagraph = beginParagraph;
    }
    @Generated(hash = 869114924)
    public BookmarkEntity() {
    }
    public long getBookmarkId() {
        return this.bookmarkId;
    }
    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
    public long getBookId() {
        return this.bookId;
    }
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
    public long getChapterId() {
        return this.chapterId;
    }
    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }
    public Date getSetTime() {
        return this.setTime;
    }
    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }
    public Date getChangeTime() {
        return this.changeTime;
    }
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    public String getBeginParagraph() {
        return this.beginParagraph;
    }
    public void setBeginParagraph(String beginParagraph) {
        this.beginParagraph = beginParagraph;
    }
    @Generated(hash = 1095264732)
    private transient Long bookEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 582316981)
    public BookEntity getBookEntity() {
        long __key = this.bookId;
        if (bookEntity__resolvedKey == null
                || !bookEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookEntityDao targetDao = daoSession.getBookEntityDao();
            BookEntity bookEntityNew = targetDao.load(__key);
            synchronized (this) {
                bookEntity = bookEntityNew;
                bookEntity__resolvedKey = __key;
            }
        }
        return bookEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 108174107)
    public void setBookEntity(@NotNull BookEntity bookEntity) {
        if (bookEntity == null) {
            throw new DaoException(
                    "To-one property 'bookId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.bookEntity = bookEntity;
            bookId = bookEntity.getId();
            bookEntity__resolvedKey = bookId;
        }
    }
    @Generated(hash = 1495122763)
    private transient Long chapterEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 825417586)
    public ChapterEntity getChapterEntity() {
        long __key = this.bookmarkId;
        if (chapterEntity__resolvedKey == null
                || !chapterEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterEntityDao targetDao = daoSession.getChapterEntityDao();
            ChapterEntity chapterEntityNew = targetDao.load(__key);
            synchronized (this) {
                chapterEntity = chapterEntityNew;
                chapterEntity__resolvedKey = __key;
            }
        }
        return chapterEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 76043514)
    public void setChapterEntity(@NotNull ChapterEntity chapterEntity) {
        if (chapterEntity == null) {
            throw new DaoException(
                    "To-one property 'bookmarkId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.chapterEntity = chapterEntity;
            bookmarkId = chapterEntity.getChapterId();
            chapterEntity__resolvedKey = bookmarkId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 265669036)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookmarkEntityDao() : null;
    }
}
