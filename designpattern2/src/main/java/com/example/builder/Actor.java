package com.example.builder;

/**
 * ��Ϸ��ɫģ��
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class Actor {

    // ��ɫ����
    private String type;
    // ��ɫ�Ա�
    private String sex;
    // ��ɫ����
    private String face;
    // ��ɫ��װ
    private String costume;
    // ��ɫ����
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
        return "��Ϸ��ɫ{" +
                "��ɫ����='" + type + '\'' +
                ", �Ա�='" + sex + '\'' +
                ", ����='" + face + '\'' +
                ", ��װ='" + costume + '\'' +
                ", ����='" + hairstyle + '\'' +
                '}';
    }
}
