package com.example.command;

/**
 * ¿¾¼¦³áÃüÁî
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class BakeChickenWingCommand extends AbstractCommand {

    public BakeChickenWingCommand(Barbecuer barbecuer) {
        super(barbecuer);
    }

    @Override
    public void executeCommand() {
        barbecuer.bakeChickenWing();
    }

    @Override
    public String toString() {
        return "¿¾¼¦³á¶©µ¥";
    }
}
