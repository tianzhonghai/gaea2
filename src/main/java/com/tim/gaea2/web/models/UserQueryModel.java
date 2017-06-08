package com.tim.gaea2.web.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by tianzhonghai on 2017/6/8.
 */
public class UserQueryModel {
    private Long id;

    private String userName;

    private String state;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
