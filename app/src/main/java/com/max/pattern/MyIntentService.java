package com.max.pattern;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class MyIntentService extends IntentService {

    public static UpdateUI updateUI;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    public interface UpdateUI{
        void updateUI(Message message);
    }

    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI = updateUIInterface;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 执行耗时操作
        Message msg1 = new Message();
        msg1.obj ="我是耗时操作";
        // 调用回调 （也可以通过广播机制完成）
        if(updateUI != null){
            updateUI.updateUI(msg1);
        }
    }
}
