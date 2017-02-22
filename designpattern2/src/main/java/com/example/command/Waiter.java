package com.example.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务员
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class Waiter {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<AbstractCommand> orders = new ArrayList<AbstractCommand>();

    // 设定订单
    public void setOrder(AbstractCommand command) {
        orders.add(command);
        System.out.println("增加订单：" + command.toString() + " \t时间：" + simpleDateFormat.format(new Date()));
    }

    // 取消订单
    public void cancelOrder(AbstractCommand command) {
        orders.remove(command);
        System.out.println("取消订单：" + command.toString() + " \t时间：" + simpleDateFormat.format(new Date()));
    }

    // 通知全部执行
    public void notifyA() {
        for (AbstractCommand command : orders) {
            command.executeCommand();
        }
    }
}
