package com.example.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

/**
 * @auther MaxLiu
 * @time 2017/2/22
 */


//附件类

/**
 * 附件类
 * <p>
 * 浅克隆 ： 不实现 Serializable
 * 深克隆 ： 实现 Serializable
 */
class Attachment implements Serializable {
    private String name; //附件名

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void download() {
        System.out.println("下载附件，文件名为" + name);
    }
}

/**
 * 修改后的工作周报
 * <p>
 * 浅克隆 ： 只会复制基本类型，附件类型不会被复制
 */
class WeeklyLog implements Cloneable {
    //为了简化设计和实现，假设一份工作周报中只有一个附件对象，实际情况中可以包含多个附件，可以通过List等集合对象来实现
    private Attachment attachment;
    private String name;
    private String date;
    private String content;

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Attachment getAttachment() {
        return (this.attachment);
    }

    public String getName() {
        return (this.name);
    }

    public String getDate() {
        return (this.date);
    }

    public String getContent() {
        return (this.content);
    }

    //使用clone()方法实现浅克隆
    public WeeklyLog clone() {
        Object obj = null;
        try {
            obj = super.clone();
            return (WeeklyLog) obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持复制！");
            return null;
        }
    }
}

/**
 * 修改后的工作周报
 * <p>
 * 深克隆 ： 全都会被复制 用实现 Serializable 序列化实现传输
 */
class WeeklyLog2 implements Serializable {
    //为了简化设计和实现，假设一份工作周报中只有一个附件对象，实际情况中可以包含多个附件，可以通过List等集合对象来实现
    private Attachment attachment;
    private String name;
    private String date;
    private String content;

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Attachment getAttachment() {
        return (this.attachment);
    }

    public String getName() {
        return (this.name);
    }

    public String getDate() {
        return (this.date);
    }

    public String getContent() {
        return (this.content);
    }

    //使用序列化技术实现深克隆
    public WeeklyLog2 deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
        //将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (WeeklyLog2) ois.readObject();
    }
}

public class PrototypeClient {
    public static void main(String args[]) {
        //浅克隆
//        WeeklyLog log_previous, log_new;
//        log_previous = new WeeklyLog(); //创建原型对象
//        Attachment attachment = new Attachment(); //创建附件对象
//        log_previous.setAttachment(attachment);  //将附件添加到周报中
//        log_new = log_previous.clone(); //调用克隆方法创建克隆对象
        // 深克隆
        WeeklyLog2 log_previous, log_new = null;
        log_previous = new WeeklyLog2(); //创建原型对象
        Attachment attachment = new Attachment(); //创建附件对象
        log_previous.setAttachment(attachment);  //将附件添加到周报中
        try {
            log_new = log_previous.deepClone(); //调用克隆方法创建克隆对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        //比较周报  输出 false  不同对象，复制成功
        System.out.println("周报是否相同？ " + (log_previous == log_new));
        //比较附件  输出 true  在内存中为同一个对象
        System.out.println("附件是否相同？ " + (log_previous.getAttachment() == log_new.getAttachment()));

//        WeekLog log_previous = new WeekLog();  //创建原型对象
//        log_previous.setName("张无忌");
//        log_previous.setDate("第12周");
//        log_previous.setContent("这周工作很忙，每天加班！");
//
//        System.out.println("****周报****");
//        System.out.println("周次：" +  log_previous.getDate());
//        System.out.println("姓名：" +  log_previous.getName());
//        System.out.println("内容：" +  log_previous.getContent());
//        System.out.println("--------------------------------");
//
//        WeekLog  log_new;
//        log_new  = log_previous.clone(); //调用克隆方法创建克隆对象
//        log_new.setDate("第13周");
//        System.out.println("****周报****");
//        System.out.println("周次：" + log_new.getDate());
//        System.out.println("姓名：" + log_new.getName());
//        System.out.println("内容：" + log_new.getContent());
//
//        System.out.println(log_previous == log_new);// false
//        System.out.println(log_previous.getDate() == log_new.getDate());// false
//        System.out.println(log_previous.getName() == log_new.getName());// true
//        System.out.println(log_previous.getContent() == log_new.getContent());// true
    }
}
