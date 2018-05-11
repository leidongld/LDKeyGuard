package com.example.leidong.ldkeyguard.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lei Dong on 2018/5/9.
 */
@Entity
public class Item {
    @Id(autoincrement = true)
    private Long id;
    private int categoryId;
    private String name;
    private String username;
    private String password;
    private String desc;


    @Generated(hash = 863695966)
    public Item(Long id, int categoryId, String name, String username,
            String password, String desc) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.desc = desc;
    }
    @Generated(hash = 1470900980)
    public Item() {
    }


    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
