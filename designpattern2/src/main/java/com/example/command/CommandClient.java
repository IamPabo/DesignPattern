package com.example.command;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class CommandClient {

    public static void main(String arg[]){
        // 开店前准备
        Barbecuer barbecuer = new Barbecuer();
        // 烤羊肉串命令
        AbstractCommand command1 = new BakeMuttonCommand(barbecuer);
        // 烤鸡翅命令
        AbstractCommand command2 = new BakeChickenWingCommand(barbecuer);
        // 烤羊肉串命令
        AbstractCommand command3 = new BakeMuttonCommand(barbecuer);
        // 烤鸡翅命令
        AbstractCommand command4 = new BakeChickenWingCommand(barbecuer);
        // 服务员
        Waiter waiter = new Waiter();

        // 开门营业
        // 点单 1
        waiter.setOrder(command1);
        // 点单 2
        waiter.setOrder(command2);
        // 点单 3
        waiter.setOrder(command3);
        // 点单 4
        waiter.setOrder(command4);
        // 取消 订单 2
        waiter.cancelOrder(command2);

        // 点菜完毕，通知厨师
        waiter.notifyA();
    }
}
