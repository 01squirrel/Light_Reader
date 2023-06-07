package com.example.qmread.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RegisteredUserEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    @Index(unique = true)
    private long registerUserId;
    private String userName;
    private String nickName;
    private String phone;
    private String password;
    private String sex;
    private String head;
    private Date createdTime;
    @Generated(hash = 38968113)
    public RegisteredUserEntity(long registerUserId, String userName,
            String nickName, String phone, String password, String sex, String head,
            Date createdTime) {
        this.registerUserId = registerUserId;
        this.userName = userName;
        this.nickName = nickName;
        this.phone = phone;
        this.password = password;
        this.sex = sex;
        this.head = head;
        this.createdTime = createdTime;
    }
    @Generated(hash = 790331900)
    public RegisteredUserEntity() {
    }
    public long getRegisterUserId() {
        return this.registerUserId;
    }
    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public Date getCreatedTime() {
        return this.createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
