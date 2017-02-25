package com.example.builder;

/**
 * 指挥者
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class ActorController {

    /**
     * 逐步构建复杂对象
     */
    public Actor construct(AbstractBuilder abstractBuilder){
        Actor actor;
        abstractBuilder.buildType();
        abstractBuilder.buildSex();
        abstractBuilder.buildFace();
        abstractBuilder.buildCostume();
        abstractBuilder.buildHairStyle();
        actor = abstractBuilder.getActor();
        return actor;
    }
}
