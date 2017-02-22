package com.example.command;

/**
 * 抽象命令类
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public abstract class AbstractCommand {
    // 羊肉串厨师
    protected Barbecuer barbecuer;

    public AbstractCommand(Barbecuer barbecuer){
        this.barbecuer = barbecuer;
    }

    /**
     * 执行命令
     */
    public abstract void executeCommand();
}
