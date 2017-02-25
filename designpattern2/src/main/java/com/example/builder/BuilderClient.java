package com.example.builder;

/**
 * 建造者模式客户端
 *
 * @auther MaxLiu
 * @time 2017/2/25
 */

public class BuilderClient {

    public static void main(String args[]){
        Actor actor;
        AbstractBuilder abstractBuilder;
        ActorController actorController = new ActorController();
        // 构建英雄
        abstractBuilder = new HeroBuilder();
        actor = actorController.construct(abstractBuilder);
        System.out.println(actor.toString() + "\n==========================");

        // 构建天使
        abstractBuilder = new AngelBuilder();
        actor = actorController.construct(abstractBuilder);
        System.out.println(actor.toString() + "\n==========================");

        // 构建恶魔
        abstractBuilder = new DevilBuilder();
        actor = actorController.construct(abstractBuilder);
        System.out.println(actor.toString() + "\n==========================");
    }
}
