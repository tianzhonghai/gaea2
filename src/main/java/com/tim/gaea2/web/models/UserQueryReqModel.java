package com.tim.gaea2.web.models;

/**
 * Created by tianzhonghai on 2017/6/27.
 */
public class UserQueryReqModel extends BasePagedReqModel {
    private Integer id;
    private String userName;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
