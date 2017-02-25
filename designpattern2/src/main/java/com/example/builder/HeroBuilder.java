package com.example.builder;

/**
 * ÓÎÏ·½ÇÉ« £º Ó¢ÐÛ
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class HeroBuilder extends AbstractBuilder {

    @Override
    public void buildType() {
        actor.setType("Ó¢ÐÛ");
    }

    @Override
    public void buildSex() {
        actor.setSex("ÄÐ");
    }

    @Override
    public void buildFace() {
        actor.setFace("Ó¢¿¡");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("¿ø¼×");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("Æ®ÒÝ");
    }
}
