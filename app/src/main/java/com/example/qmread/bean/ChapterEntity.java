package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.qmread.greendao.gen.DaoSession;
import com.example.qmread.greendao.gen.BookmarkEntityDao;
import com.example.qmread.greendao.gen.BookEntityDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.example.qmread.greendao.gen.ChapterEntityDao;

@Entity
public class ChapterEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 4L;
    @Id(autoincrement = true)
    @Index(unique = true)
    private long chapterId;
    private String chapterName;
    private Date updateTime;
    private Integer totalWords;
    private Integer totalComments;
    private Integer bookmarks;
    @ToOne(joinProperty = "chapterId")
    private BookEntity bookEntity;
    @ToMany(referencedJoinProperty = "bookmarkId")
    private List<BookmarkEntity> bookmarkEntityList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 592933020)
    private transient ChapterEntityDao myDao;
    @Generated(hash = 486146484)
    public ChapterEntity(long chapterId, String chapterName, Date updateTime,
            Integer totalWords, Integer totalComments, Integer bookmarks) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.updateTime = updateTime;
        this.totalWords = totalWords;
        this.totalComments = totalComments;
        this.bookmarks = bookmarks;
    }
    @Generated(hash = 1142697545)
    public ChapterEntity() {
    }
    public long getChapterId() {
        return this.chapterId;
    }
    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getTotalWords() {
        return this.totalWords;
    }
    public void setTotalWords(Integer totalWords) {
        this.totalWords = totalWords;
    }
    public Integer getTotalComments() {
        return this.totalComments;
    }
    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }
    public Integer getBookmarks() {
        return this.bookmarks;
    }
    public void setBookmarks(Integer bookmarks) {
        this.bookmarks = bookmarks;
    }
    @Generated(hash = 1095264732)
    private transient Long bookEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 623153771)
    public BookEntity getBookEntity() {
        long __key = this.chapterId;
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
    @Generated(hash = 1905880730)
    public void setBookEntity(@NotNull BookEntity bookEntity) {
        if (bookEntity == null) {
            throw new DaoException(
                    "To-one property 'chapterId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.bookEntity = bookEntity;
            chapterId = bookEntity.getId();
            bookEntity__resolvedKey = chapterId;
        }
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 990855813)
    public List<BookmarkEntity> getBookmarkEntityList() {
        if (bookmarkEntityList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookmarkEntityDao targetDao = daoSession.getBookmarkEntityDao();
            List<BookmarkEntity> bookmarkEntityListNew = targetDao
                    ._queryChapterEntity_BookmarkEntityList(chapterId);
            synchronized (this) {
                if (bookmarkEntityList == null) {
                    bookmarkEntityList = bookmarkEntityListNew;
                }
            }
        }
        return bookmarkEntityList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1203234835)
    public synchronized void resetBookmarkEntityList() {
        bookmarkEntityList = null;
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
    @Generated(hash = 739587172)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChapterEntityDao() : null;
    }
}
