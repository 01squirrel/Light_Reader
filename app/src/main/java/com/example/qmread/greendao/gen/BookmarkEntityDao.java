package com.example.qmread.greendao.gen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.ChapterEntity;

import com.example.qmread.bean.BookmarkEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOKMARK_ENTITY".
*/
public class BookmarkEntityDao extends AbstractDao<BookmarkEntity, Long> {

    public static final String TABLENAME = "BOOKMARK_ENTITY";

    /**
     * Properties of entity BookmarkEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BookmarkId = new Property(0, long.class, "bookmarkId", true, "_id");
        public final static Property BookId = new Property(1, long.class, "bookId", false, "BOOK_ID");
        public final static Property ChapterId = new Property(2, long.class, "chapterId", false, "CHAPTER_ID");
        public final static Property SetTime = new Property(3, java.util.Date.class, "setTime", false, "SET_TIME");
        public final static Property ChangeTime = new Property(4, java.util.Date.class, "changeTime", false, "CHANGE_TIME");
        public final static Property BeginParagraph = new Property(5, String.class, "beginParagraph", false, "BEGIN_PARAGRAPH");
    }

    private DaoSession daoSession;

    private Query<BookmarkEntity> chapterEntity_BookmarkEntityListQuery;

    public BookmarkEntityDao(DaoConfig config) {
        super(config);
    }
    
    public BookmarkEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOKMARK_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: bookmarkId
                "\"BOOK_ID\" INTEGER NOT NULL ," + // 1: bookId
                "\"CHAPTER_ID\" INTEGER NOT NULL ," + // 2: chapterId
                "\"SET_TIME\" INTEGER," + // 3: setTime
                "\"CHANGE_TIME\" INTEGER," + // 4: changeTime
                "\"BEGIN_PARAGRAPH\" TEXT);"); // 5: beginParagraph
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_BOOKMARK_ENTITY__id ON \"BOOKMARK_ENTITY\"" +
                " (\"_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOKMARK_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookmarkEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getBookmarkId());
        stmt.bindLong(2, entity.getBookId());
        stmt.bindLong(3, entity.getChapterId());
 
        java.util.Date setTime = entity.getSetTime();
        if (setTime != null) {
            stmt.bindLong(4, setTime.getTime());
        }
 
        java.util.Date changeTime = entity.getChangeTime();
        if (changeTime != null) {
            stmt.bindLong(5, changeTime.getTime());
        }
 
        String beginParagraph = entity.getBeginParagraph();
        if (beginParagraph != null) {
            stmt.bindString(6, beginParagraph);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookmarkEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getBookmarkId());
        stmt.bindLong(2, entity.getBookId());
        stmt.bindLong(3, entity.getChapterId());
 
        java.util.Date setTime = entity.getSetTime();
        if (setTime != null) {
            stmt.bindLong(4, setTime.getTime());
        }
 
        java.util.Date changeTime = entity.getChangeTime();
        if (changeTime != null) {
            stmt.bindLong(5, changeTime.getTime());
        }
 
        String beginParagraph = entity.getBeginParagraph();
        if (beginParagraph != null) {
            stmt.bindString(6, beginParagraph);
        }
    }

    @Override
    protected final void attachEntity(BookmarkEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public BookmarkEntity readEntity(Cursor cursor, int offset) {
        BookmarkEntity entity = new BookmarkEntity( //
            cursor.getLong(offset + 0), // bookmarkId
            cursor.getLong(offset + 1), // bookId
            cursor.getLong(offset + 2), // chapterId
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // setTime
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // changeTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // beginParagraph
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookmarkEntity entity, int offset) {
        entity.setBookmarkId(cursor.getLong(offset + 0));
        entity.setBookId(cursor.getLong(offset + 1));
        entity.setChapterId(cursor.getLong(offset + 2));
        entity.setSetTime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setChangeTime(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setBeginParagraph(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BookmarkEntity entity, long rowId) {
        entity.setBookmarkId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BookmarkEntity entity) {
        if(entity != null) {
            return entity.getBookmarkId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BookmarkEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "bookmarkEntityList" to-many relationship of ChapterEntity. */
    public List<BookmarkEntity> _queryChapterEntity_BookmarkEntityList(long bookmarkId) {
        synchronized (this) {
            if (chapterEntity_BookmarkEntityListQuery == null) {
                QueryBuilder<BookmarkEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.BookmarkId.eq(null));
                chapterEntity_BookmarkEntityListQuery = queryBuilder.build();
            }
        }
        Query<BookmarkEntity> query = chapterEntity_BookmarkEntityListQuery.forCurrentThread();
        query.setParameter(0, bookmarkId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBookEntityDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getChapterEntityDao().getAllColumns());
            builder.append(" FROM BOOKMARK_ENTITY T");
            builder.append(" LEFT JOIN BOOK_ENTITY T0 ON T.\"BOOK_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN CHAPTER_ENTITY T1 ON T.\"_id\"=T1.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected BookmarkEntity loadCurrentDeep(Cursor cursor, boolean lock) {
        BookmarkEntity entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        BookEntity bookEntity = loadCurrentOther(daoSession.getBookEntityDao(), cursor, offset);
         if(bookEntity != null) {
            entity.setBookEntity(bookEntity);
        }
        offset += daoSession.getBookEntityDao().getAllColumns().length;

        ChapterEntity chapterEntity = loadCurrentOther(daoSession.getChapterEntityDao(), cursor, offset);
         if(chapterEntity != null) {
            entity.setChapterEntity(chapterEntity);
        }

        return entity;    
    }

    public BookmarkEntity loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<BookmarkEntity> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BookmarkEntity> list = new ArrayList<BookmarkEntity>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<BookmarkEntity> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<BookmarkEntity> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
