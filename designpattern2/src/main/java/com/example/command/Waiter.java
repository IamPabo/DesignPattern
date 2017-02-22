package com.example.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ����Ա
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class Waiter {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<AbstractCommand> orders = new ArrayList<AbstractCommand>();

    // �趨����
    public void setOrder(AbstractCommand command) {
        orders.add(command);
        System.out.println("���Ӷ�����" + command.toString() + " \tʱ�䣺" + simpleDateFormat.format(new Date()));
    }

    // ȡ������
    public void cancelOrder(AbstractCommand command) {
        orders.remove(command);
        System.out.println("ȡ��������" + command.toString() + " \tʱ�䣺" + simpleDateFormat.format(new Date()));
    }

    // ֪ͨȫ��ִ��
    public void notifyA() {
        for (AbstractCommand command : orders) {
            command.executeCommand();
        }
    }
}
