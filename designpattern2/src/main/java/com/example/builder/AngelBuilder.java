package com.example.builder;

/**
 * ���彨���ߣ���ʹ��ɫ
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class AngelBuilder extends AbstractBuilder {

    @Override
    public void buildType() {
        actor.setType("��ʹ");
    }

    @Override
    public void buildSex() {
        actor.setSex("Ů");
    }

    @Override
    public void buildFace() {
        actor.setFace("Ư��");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("��ȹ");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("���糤��");
    }
}
