package com.network;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> annotations = new ArrayList<>();

        annotations.add("topic1");
        annotations.add("topic2");
        annotations.add("topic3");
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
        intent.setType("text/plain");
        intent.putStringArrayListExtra(
                Intent.EXTRA_CONTENT_ANNOTATIONS,
                annotations
        );
//        startActivity(intent);

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(intent, "分享到"));
        TextView textView = findViewById(R.id.textView);
        Log.i("Tag, ", NetUtils.getNetWorkState(this) + "!");

        switch (NetUtils.getNetWorkState(this)) {

            case NetUtils.NETWORK_MOBILE:
                EventBus.getDefault().post(new NetWorkEvent("手机流量!"));
                textView.setText("Hello, "  + "手机流量!");
                break;
            case NetUtils.NETWORK_NONE:
                EventBus.getDefault().post(new NetWorkEvent("无网络!"));
                textView.setText("Hello, "  + "无网络!");
                break;
            case NetUtils.NETWORK_WIFI:
                EventBus.getDefault().post(new NetWorkEvent("无线网!"));
                textView.setText("Hello, "  + "无线网!");
                break;

        }
    }


    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
            enterPictureInPictureMode();
        else
            super.onBackPressed();

    }
    /*    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }*/

    // This method will be called when a onNetListener is posted
    @Subscribe
    public void onNetListener(NetWorkEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.textView);
        Log.i("Tag, ", event.message + "!");
        textView.setText("Hello, " + event.message + "!");
    }

    public void click(View view) {
        Log.i("test", "测");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void sayHello(View v) {
        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);
        textView.setText("Hello, " + editText.getText().toString() + "!");
    }

}
