package com.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by gengshibiao
 *
 * @author gengshibiao
 * @mail 1141213256@qq.com
 * @outline 作用：网路状态广播接收类
 */

public class NetStateReceiver extends BroadcastReceiver {
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)) {
            switch (NetUtils.getNetWorkState(context)) {
                case NetUtils.NETWORK_MOBILE:
                    EventBus.getDefault().post(new NetWorkEvent("手机流量!"));
                    break;
                case NetUtils.NETWORK_NONE:
                    EventBus.getDefault().post(new NetWorkEvent("无网络!"));
                    break;
                case NetUtils.NETWORK_WIFI:
                    EventBus.getDefault().post(new NetWorkEvent("无线网!"));
                    break;

            }
        }
    }

}

