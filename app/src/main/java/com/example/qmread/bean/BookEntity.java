package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.example.qmread.greendao.gen.DaoSession;
import com.example.qmread.greendao.gen.ChapterEntityDao;
import com.example.qmread.greendao.gen.BookEntityDao;

@Entity
public class BookEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 3L;
    @Id(autoincrement = true)
    private Long id;
    private String novelUrl;
    private String name;
    private String cover;
    private int chapterNum;
    private int chapterIndex;
    private int position;
    private String type;
    private int secondPosition;
    @ToMany(referencedJoinProperty = "chapterId")
    private List<ChapterEntity> chapters;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 492744051)
    private transient BookEntityDao myDao;
    @Generated(hash = 195621389)
    public BookEntity(Long id, String novelUrl, String name, String cover,
            int chapterNum, int chapterIndex, int position, String type,
            int secondPosition) {
        this.id = id;
        this.novelUrl = novelUrl;
        this.name = name;
        this.cover = cover;
        this.chapterNum = chapterNum;
        this.chapterIndex = chapterIndex;
        this.position = position;
        this.type = type;
        this.secondPosition = secondPosition;
    }
    @Generated(hash = 1373651409)
    public BookEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNovelUrl() {
        return this.novelUrl;
    }
    public void setNovelUrl(String novelUrl) {
        this.novelUrl = novelUrl;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public int getChapterNum() {
        return this.chapterNum;
    }
    public void setChapterNum(int chapterNum) {
        this.chapterNum = chapterNum;
    }
    public int getChapterIndex() {
        return this.chapterIndex;
    }
    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getSecondPosition() {
        return this.secondPosition;
    }
    public void setSecondPosition(int secondPosition) {
        this.secondPosition = secondPosition;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 314529455)
    public List<ChapterEntity> getChapters() {
        if (chapters == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterEntityDao targetDao = daoSession.getChapterEntityDao();
            List<ChapterEntity> chaptersNew = targetDao
                    ._queryBookEntity_Chapters(id);
            synchronized (this) {
                if (chapters == null) {
                    chapters = chaptersNew;
                }
            }
        }
        return chapters;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 936914273)
    public synchronized void resetChapters() {
        chapters = null;
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
    @Generated(hash = 1364325922)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookEntityDao() : null;
    }
}
