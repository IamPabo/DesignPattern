package com.example.builder;

/**
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class DevilBuilder extends AbstractBuilder {
    @Override
    public void buildType() {
        actor.setType("��ħ");
    }

    @Override
    public void buildSex() {
        actor.setSex("��");
    }

    @Override
    public void buildFace() {
        actor.setFace("��ª");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("����");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("��ͷ");
    }
}
