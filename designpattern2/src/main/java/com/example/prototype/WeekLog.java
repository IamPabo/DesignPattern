package com.example.prototype;

/**
 * 原型模式 ： 实现 Cloneable 接口，表示该对象可以被 clone
 *           调用 super.clone() 返回克隆后的对象
 *
 * @auther MaxLiu
 * @time 2017/2/22
 */

public class WeekLog implements Cloneable{
    private String name;
    private String date;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 克隆方法，使用 Java 提供的克隆机制
     * @return WeekLog 的克隆对象
     */
    public WeekLog clone(){
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (WeekLog)obj;
    }
}
