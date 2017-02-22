package com.example.command;

/**
 * �����⴮����
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class BakeMuttonCommand extends AbstractCommand {

    public BakeMuttonCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }

    @Override
    public void executeCommand() {
        barbecuer.bakeMutton();
    }

    @Override
    public String toString() {
        return "�����⴮����";
    }
}
