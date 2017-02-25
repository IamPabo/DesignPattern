package com.example.builder;

/**
 * 游戏角色模型
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class Actor {

    // 角色类型
    private String type;
    // 角色性别
    private String sex;
    // 角色脸型
    private String face;
    // 角色服装
    private String costume;
    // 角色发型
    private String hairstyle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCostume() {
        return costume;
    }

    public void setCostume(String costume) {
        this.costume = costume;
    }

    public String getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(String hairstyle) {
        this.hairstyle = hairstyle;
    }

    @Override
    public String toString() {
        return "游戏角色{" +
                "角色类型='" + type + '\'' +
                ", 性别='" + sex + '\'' +
                ", 脸型='" + face + '\'' +
                ", 服装='" + costume + '\'' +
                ", 发型='" + hairstyle + '\'' +
                '}';
    }
}
