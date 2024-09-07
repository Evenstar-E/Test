package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AlarmAlertActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 播放音频
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.setLooping(true);  // 设置循环播放
        mediaPlayer.start();

        // 显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("闹钟提醒")
                .setMessage("该起床了！")
                .setCancelable(false)
                .setPositiveButton("关闭", (dialog, which) -> {
                    stopAlarm();  // 停止播放音频
                    finish();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void stopAlarm() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm();  // 在 Activity 销毁时停止音频播放
    }
}
