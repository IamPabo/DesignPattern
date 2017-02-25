package com.example.builder;

/**
 * 具体建造者，天使角色
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class AngelBuilder extends AbstractBuilder {

    @Override
    public void buildType() {
        actor.setType("天使");
    }

    @Override
    public void buildSex() {
        actor.setSex("女");
    }

    @Override
    public void buildFace() {
        actor.setFace("漂亮");
    }

    @Override
    public void buildCostume() {
        actor.setCostume("白裙");
    }

    @Override
    public void buildHairStyle() {
        actor.setHairstyle("披肩长发");
    }
}
