package com.example.builder;

/**
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class DevilBuilder extends AbstractBuilder {
    @Override
    public void buildType() {
        actor.setType("¶ñÄ§");
    }

    @Override
    public void buildSex() {
        actor.setSex("Ñý");
    }

    @Override
    public void buildFace() {
        actor.setFace("³óÂª");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("ºÚÒÂ");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("¹âÍ·");
    }
}
