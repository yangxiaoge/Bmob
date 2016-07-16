package com.sample.wmz.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by wmz on 16-7-16.
 */
public class PersonProfile extends BmobObject{
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
