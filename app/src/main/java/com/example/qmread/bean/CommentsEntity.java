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
import com.example.qmread.greendao.gen.RegisteredUserEntityDao;
import com.example.qmread.greendao.gen.CommentsEntityDao;

@Entity
public class CommentsEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 7L;
    @Id(autoincrement = true)
    @Index(unique = true)
    private long id;
    private long registerId;
    @ToOne(joinProperty = "registerId")
    private RegisteredUserEntity userEntity;
    private long bookId;
    @ToOne(joinProperty = "bookId")
    private BookEntity bookEntity;
    private long chapterId;
    @ToOne(joinProperty = "chapterId")
    private ChapterEntity chapterEntity;
    private Date createdTime;
    private String detail;
    private Integer current;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 308322765)
    private transient CommentsEntityDao myDao;
    @Generated(hash = 1250865483)
    public CommentsEntity(long id, long registerId, long bookId, long chapterId,
            Date createdTime, String detail, Integer current) {
        this.id = id;
        this.registerId = registerId;
        this.bookId = bookId;
        this.chapterId = chapterId;
        this.createdTime = createdTime;
        this.detail = detail;
        this.current = current;
    }
    @Generated(hash = 778156047)
    public CommentsEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getRegisterId() {
        return this.registerId;
    }
    public void setRegisterId(long registerId) {
        this.registerId = registerId;
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
    public Date getCreatedTime() {
        return this.createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public String getDetail() {
        return this.detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Integer getCurrent() {
        return this.current;
    }
    public void setCurrent(Integer current) {
        this.current = current;
    }
    @Generated(hash = 1473225700)
    private transient Long userEntity__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 348504759)
    public RegisteredUserEntity getUserEntity() {
        long __key = this.registerId;
        if (userEntity__resolvedKey == null
                || !userEntity__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegisteredUserEntityDao targetDao = daoSession
                    .getRegisteredUserEntityDao();
            RegisteredUserEntity userEntityNew = targetDao.load(__key);
            synchronized (this) {
                userEntity = userEntityNew;
                userEntity__resolvedKey = __key;
            }
        }
        return userEntity;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2039695862)
    public void setUserEntity(@NotNull RegisteredUserEntity userEntity) {
        if (userEntity == null) {
            throw new DaoException(
                    "To-one property 'registerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.userEntity = userEntity;
            registerId = userEntity.getRegisterUserId();
            userEntity__resolvedKey = registerId;
        }
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
    @Generated(hash = 1861289844)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentsEntityDao() : null;
    }
}
