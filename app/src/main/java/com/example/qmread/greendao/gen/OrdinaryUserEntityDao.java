package com.example.qmread.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.qmread.bean.OrdinaryUserEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORDINARY_USER_ENTITY".
*/
public class OrdinaryUserEntityDao extends AbstractDao<OrdinaryUserEntity, Long> {

    public static final String TABLENAME = "ORDINARY_USER_ENTITY";

    /**
     * Properties of entity OrdinaryUserEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property OrdinaryUserId = new Property(0, long.class, "ordinaryUserId", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property CreateTime = new Property(3, java.util.Date.class, "createTime", false, "CREATE_TIME");
    }


    public OrdinaryUserEntityDao(DaoConfig config) {
        super(config);
    }
    
    public OrdinaryUserEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORDINARY_USER_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: ordinaryUserId
                "\"NAME\" TEXT," + // 1: name
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"CREATE_TIME\" INTEGER);"); // 3: createTime
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_ORDINARY_USER_ENTITY__id ON \"ORDINARY_USER_ENTITY\"" +
                " (\"_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORDINARY_USER_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OrdinaryUserEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getOrdinaryUserId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OrdinaryUserEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getOrdinaryUserId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public OrdinaryUserEntity readEntity(Cursor cursor, int offset) {
        OrdinaryUserEntity entity = new OrdinaryUserEntity( //
            cursor.getLong(offset + 0), // ordinaryUserId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // createTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OrdinaryUserEntity entity, int offset) {
        entity.setOrdinaryUserId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OrdinaryUserEntity entity, long rowId) {
        entity.setOrdinaryUserId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OrdinaryUserEntity entity) {
        if(entity != null) {
            return entity.getOrdinaryUserId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OrdinaryUserEntity entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
