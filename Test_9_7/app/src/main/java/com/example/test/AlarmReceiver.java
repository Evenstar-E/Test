package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmReceiver", "闹钟已触发");
        Intent alarmIntent = new Intent(context, AlarmAlertActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // 确保在非活动上下文中启动活动
        context.startActivity(alarmIntent);
    }
}
