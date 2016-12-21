package cs601.socketServer.models;

import java.sql.Date;

/**
 * Created by Yiding Liu on 11/14/2016.
 */
public class UserModel {
    private String name;
    private int userId;
    private String password;
    private Date lastLoginTime;

    public UserModel(int userId, String name) {
        this.name = name;
        this.userId = userId;
    }

    public UserModel(String name, int userId, String password) {
        this.name = name;
        this.userId = userId;
        this.password = password;
    }
    public UserModel(String name, int userId, Date lastLoginTime) {
        this.name = name;
        this.userId = userId;
        this.lastLoginTime=lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public Date getLastLoginTime(){return lastLoginTime;}
}
