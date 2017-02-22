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


//������

/**
 * ������
 * <p>
 * ǳ��¡ �� ��ʵ�� Serializable
 * ���¡ �� ʵ�� Serializable
 */
class Attachment implements Serializable {
    private String name; //������

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void download() {
        System.out.println("���ظ������ļ���Ϊ" + name);
    }
}

/**
 * �޸ĺ�Ĺ����ܱ�
 * <p>
 * ǳ��¡ �� ֻ�Ḵ�ƻ������ͣ��������Ͳ��ᱻ����
 */
class WeeklyLog implements Cloneable {
    //Ϊ�˼���ƺ�ʵ�֣�����һ�ݹ����ܱ���ֻ��һ����������ʵ������п��԰����������������ͨ��List�ȼ��϶�����ʵ��
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

    //ʹ��clone()����ʵ��ǳ��¡
    public WeeklyLog clone() {
        Object obj = null;
        try {
            obj = super.clone();
            return (WeeklyLog) obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("��֧�ָ��ƣ�");
            return null;
        }
    }
}

/**
 * �޸ĺ�Ĺ����ܱ�
 * <p>
 * ���¡ �� ȫ���ᱻ���� ��ʵ�� Serializable ���л�ʵ�ִ���
 */
class WeeklyLog2 implements Serializable {
    //Ϊ�˼���ƺ�ʵ�֣�����һ�ݹ����ܱ���ֻ��һ����������ʵ������п��԰����������������ͨ��List�ȼ��϶�����ʵ��
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

    //ʹ�����л�����ʵ�����¡
    public WeeklyLog2 deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
        //������д������
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        //�����������ȡ��
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (WeeklyLog2) ois.readObject();
    }
}

public class PrototypeClient {
    public static void main(String args[]) {
        //ǳ��¡
//        WeeklyLog log_previous, log_new;
//        log_previous = new WeeklyLog(); //����ԭ�Ͷ���
//        Attachment attachment = new Attachment(); //������������
//        log_previous.setAttachment(attachment);  //��������ӵ��ܱ���
//        log_new = log_previous.clone(); //���ÿ�¡����������¡����
        // ���¡
        WeeklyLog2 log_previous, log_new = null;
        log_previous = new WeeklyLog2(); //����ԭ�Ͷ���
        Attachment attachment = new Attachment(); //������������
        log_previous.setAttachment(attachment);  //��������ӵ��ܱ���
        try {
            log_new = log_previous.deepClone(); //���ÿ�¡����������¡����
        } catch (Exception e) {
            e.printStackTrace();
        }
        //�Ƚ��ܱ�  ��� false  ��ͬ���󣬸��Ƴɹ�
        System.out.println("�ܱ��Ƿ���ͬ�� " + (log_previous == log_new));
        //�Ƚϸ���  ��� true  ���ڴ���Ϊͬһ������
        System.out.println("�����Ƿ���ͬ�� " + (log_previous.getAttachment() == log_new.getAttachment()));

//        WeekLog log_previous = new WeekLog();  //����ԭ�Ͷ���
//        log_previous.setName("���޼�");
//        log_previous.setDate("��12��");
//        log_previous.setContent("���ܹ�����æ��ÿ��Ӱ࣡");
//
//        System.out.println("****�ܱ�****");
//        System.out.println("�ܴΣ�" +  log_previous.getDate());
//        System.out.println("������" +  log_previous.getName());
//        System.out.println("���ݣ�" +  log_previous.getContent());
//        System.out.println("--------------------------------");
//
//        WeekLog  log_new;
//        log_new  = log_previous.clone(); //���ÿ�¡����������¡����
//        log_new.setDate("��13��");
//        System.out.println("****�ܱ�****");
//        System.out.println("�ܴΣ�" + log_new.getDate());
//        System.out.println("������" + log_new.getName());
//        System.out.println("���ݣ�" + log_new.getContent());
//
//        System.out.println(log_previous == log_new);// false
//        System.out.println(log_previous.getDate() == log_new.getDate());// false
//        System.out.println(log_previous.getName() == log_new.getName());// true
//        System.out.println(log_previous.getContent() == log_new.getContent());// true
    }
}
