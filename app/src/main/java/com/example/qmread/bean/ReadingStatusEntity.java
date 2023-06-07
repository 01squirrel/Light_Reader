package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.qmread.greendao.gen.DaoSession;
import com.example.qmread.greendao.gen.OrdinaryUserEntityDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.example.qmread.greendao.gen.RegisteredUserEntityDao;
import com.example.qmread.greendao.gen.ChapterEntityDao;
import com.example.qmread.greendao.gen.BookEntityDao;
import com.example.qmread.greendao.gen.ReadingStatusEntityDao;

@Entity
public class ReadingStatusEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 6L;
    @Id(autoincrement = true)
    @Index(unique = true)
    private long statusId;
    private long bookId;
    @ToOne(joinProperty = "bookId")
    private BookEntity bookEntity;
    private long chapterId;
    @ToOne(joinProperty = "chapterId")
    private ChapterEntity chapterEntity;
    private Integer readingDuration;
    private Integer readingLocation;
    private long userId;
    @ToOne(joinProperty = "userId")
    private RegisteredUserEntity registeredUserEntity;
    @ToOne(joinProperty = "userId")
    private OrdinaryUserEntity ordinaryUserEntity;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 948455985)
    private transient ReadingStatusEntityDao myDao;
    @Generated(hash = 1964627743)
    public ReadingStatusEntity(long statusId, long bookId, long chapterId,
            Integer readingDuration, Integer readingLocation, long userId) {
        this.statusId = statusId;
        this.bookId = bookId;
        this.chapterId = chapterId;
        this.readingDuration = readingDuration;
        this.readingLocation = readingLocation;
        this.userId = userId;
    }
    @Generated(hash = 24470810)
    public ReadingStatusEntity() {
    }
    public long getStatusId() {
        return this.statusId;
    }
    public void setStatusId(long statusId) {
        this.statusId = statusId;
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
    public Integer getReadingDuration() {
        return this.readingDuration;
    }
    public void setReadingDuration(Integer readingDuration) {
        this.readingDuration = readingDuration;
    }
    public Integer getReadingLocation() {
        return this.readingLocation;
    }
    public void setReadingLocation(Integer readingLocation) {
        this.readingLocation = readingLocation;
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
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
    @Generated(hash = 1635254183)
    public ChapterEntity getChapterEntity() {
        long __key = this.chapterId;
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
    @Generated(hash = 731963691)
    public void setChapterEntity(@NotNull ChapterEntity chapterEntity) {
        if (chapterEntity == null) {
            throw new DaoException(
                    "To-one property 'chapterId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.chapterEntity = chapterEntity;
            chapterId = chapterEntity.getChapterId();
            chapterEntity__resolvedKey = chapterId;
        }
    }
    @Generated(hash = 766515384)
    private transient Long registeredUserEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 659246941)
    public RegisteredUserEntity getRegisteredUserEntity() {
        long __key = this.userId;
        if (registeredUserEntity__resolvedKey == null
                || !registeredUserEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegisteredUserEntityDao targetDao = daoSession
                    .getRegisteredUserEntityDao();
            RegisteredUserEntity registeredUserEntityNew = targetDao.load(__key);
            synchronized (this) {
                registeredUserEntity = registeredUserEntityNew;
                registeredUserEntity__resolvedKey = __key;
            }
        }
        return registeredUserEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1975276872)
    public void setRegisteredUserEntity(
            @NotNull RegisteredUserEntity registeredUserEntity) {
        if (registeredUserEntity == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.registeredUserEntity = registeredUserEntity;
            userId = registeredUserEntity.getRegisterUserId();
            registeredUserEntity__resolvedKey = userId;
        }
    }
    @Generated(hash = 2144422470)
    private transient Long ordinaryUserEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1668213595)
    public OrdinaryUserEntity getOrdinaryUserEntity() {
        long __key = this.userId;
        if (ordinaryUserEntity__resolvedKey == null
                || !ordinaryUserEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrdinaryUserEntityDao targetDao = daoSession.getOrdinaryUserEntityDao();
            OrdinaryUserEntity ordinaryUserEntityNew = targetDao.load(__key);
            synchronized (this) {
                ordinaryUserEntity = ordinaryUserEntityNew;
                ordinaryUserEntity__resolvedKey = __key;
            }
        }
        return ordinaryUserEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1333380143)
    public void setOrdinaryUserEntity(
            @NotNull OrdinaryUserEntity ordinaryUserEntity) {
        if (ordinaryUserEntity == null) {
            throw new DaoException(
                    "To-one property 'userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ordinaryUserEntity = ordinaryUserEntity;
            userId = ordinaryUserEntity.getOrdinaryUserId();
            ordinaryUserEntity__resolvedKey = userId;
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
    @Generated(hash = 82832856)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getReadingStatusEntityDao() : null;
    }
}
