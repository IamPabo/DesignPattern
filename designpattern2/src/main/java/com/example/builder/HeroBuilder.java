package com.example.builder;

/**
 * ��Ϸ��ɫ �� Ӣ��
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class HeroBuilder extends AbstractBuilder {

    @Override
    public void buildType() {
        actor.setType("Ӣ��");
    }

    @Override
    public void buildSex() {
        actor.setSex("��");
    }

    @Override
    public void buildFace() {
        actor.setFace("Ӣ��");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("����");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("Ʈ��");
    }
}
