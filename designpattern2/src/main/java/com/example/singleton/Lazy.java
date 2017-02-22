package com.example.singleton;

/**
 * ����ģʽ�е�����ģʽ���ڵ���ʱ�Ż�ʵ�����������ʱ������ʵ���������ּ���
 * �ֳ�����ʱ���ؼ���������Ҫ��ʱ���ڼ���ʵ����Ϊ�˱������߳�ͬʱ���� get
 * ʵ���ķ�����ʹ�ùؼ��� synchronized ʵ�֡�
 *
 * �ŵ�  �ڵ�һ��ʹ��ʱ����������һֱռ��ϵͳ��Դ��ʵ�����ӳټ��أ�
 *
 * ȱ��  ���Ǳ��봦��ö���߳�ͬʱ���ʵ����⣬�ر��ǵ���������Ϊ��Դ��������
 *      ��ʵ����ʱ��Ȼ�漰��Դ��ʼ��������Դ��ʼ�����п��ܺķѴ���ʱ�䣬
 *      ����ζ�ų��ֶ��߳�ͬʱ�״����ô���Ļ��ʱ�ýϴ�
 *      ��Ҫͨ��˫�ؼ�������Ȼ��ƽ��п��ƣ��⽫����ϵͳ�����ܵ�һ��Ӱ�졣
 *
 * @auther MaxLiu
 * @time 2017/2/21
 */

public class Lazy {
    private static Lazy lazy;
    private static int count;

    private Lazy(){
        System.out.println("����ʵ��");
    }

    /**
     * ��������ÿ�ε��ø÷�������Ҫ�����߳������жϣ��ڶ��̲߳�������£��ᵼ�����ܴ�󽵵͡�
     * ������Ҫ������ģʽ���иĽ�{@link #getLazyImprove()}
     *
     * @return lazyʵ��
     */
    public synchronized static Lazy getLazy() {
        if(lazy == null){
            lazy = new Lazy();
        }
        return lazy;
    }

    /**
     * ����ģʽ�ĸĽ��棬�ѷ�������Ϊ���� synchronized��ò�������Ѿ�����������ǻ��ǻ����
     * �����Ĳ�Ψһ��
     * ԭ�� �� ������߳�A���߳�Bͬʱ���� getLazyImprove ���������Ҷ��ж� lazy == null
     * A���õ�����ʵ����lazy�����غ� B �õ�����ʵ����lazy�����ء���ʱ����������������ʵ����
     * �޷���֤Ψһʵ����{@link #getLazyFinalChapter()}
     *
     * @return lazyʵ��
     */
    public static Lazy getLazyImprove(){
        if(lazy == null){
            synchronized (Lazy.class){
                lazy = new Lazy();
            }
        }
        return lazy;
    }

    /**
     * ʹ��˫�������ƣ���ʱ�߳�A�õ������� lazy ʵ�����߳�B�õ��������ж��Ƿ���� lazy
     * ����ʱ lazy �Ѿ�ʵ�������߳�Aʵ�������أ��������߳�B��ִ��ʵ������ʵ����Ψһʵ����
     *
     * @return lazyʵ��
     */
    public static Lazy getLazyFinalChapter(){
        if(lazy == null){
            synchronized (Lazy.class){
                if(lazy == null) {
                    lazy = new Lazy();
                }
            }
        }
        return lazy;
    }
}
