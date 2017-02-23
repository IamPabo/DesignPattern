package com.max.pattern;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 创建一个线程，县城名字 : handlerThreadTest
        mHandlerThread = new HandlerThread("handlerThreadTest");
        mHandlerThread.start();

        // Handler 接收消息
        final Handler mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Test", "收到 " + msg.obj.toString() + " 在 "
                        + Thread.currentThread().getName());
            }
        };
        mTextView = (TextView) findViewById(R.id.text_view);
        // 主线程发出消息
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.obj = "第一条信息";
                mHandler.sendMessage(msg);
                Log.e("Test", "发出 " + msg.obj.toString() + " 在 "
                        + Thread.currentThread().getName());
            }
        });
        // 子线程发出消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = "第二条信息";
                mHandler.sendMessage(msg);
                Log.e("Test", "发出 " + msg.obj.toString() + " 在 "
                        + Thread.currentThread().getName());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
