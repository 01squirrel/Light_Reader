package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrdinaryUserEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 2L;
    @Id
    @Index(unique = true)
    private long ordinaryUserId;
    private String name;
    private String nickName;
    private Date createTime;
    @Generated(hash = 455334169)
    public OrdinaryUserEntity(long ordinaryUserId, String name, String nickName,
            Date createTime) {
        this.ordinaryUserId = ordinaryUserId;
        this.name = name;
        this.nickName = nickName;
        this.createTime = createTime;
    }
    @Generated(hash = 1615930611)
    public OrdinaryUserEntity() {
    }
    public long getOrdinaryUserId() {
        return this.ordinaryUserId;
    }
    public void setOrdinaryUserId(long ordinaryUserId) {
        this.ordinaryUserId = ordinaryUserId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
