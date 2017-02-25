package com.example.builder;

/**
 * 抽象建造者
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public abstract class AbstractBuilder {

    protected Actor actor = new Actor();

    public abstract void buildType();
    public abstract void buildSex();
    public abstract void buildFace();
    public abstract void buildCostume();
    public abstract void buildHairStyle();

    /**
     * 工厂方法 返回一个完整的游戏角色
     * @return 游戏角色
     */
    public Actor getActor(){
        return actor;
    }
}
