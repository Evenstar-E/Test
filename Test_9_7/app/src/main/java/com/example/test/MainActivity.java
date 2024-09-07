package com.example.test;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private TextView alarmInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 检查是否有设置精确闹钟的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
        timePicker = findViewById(R.id.timePicker);
        Button setAlarmButton = findViewById(R.id.setAlarmButton);
        alarmInfoTextView = findViewById(R.id.alarmInfoTextView);

        setAlarmButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);

            long timeInMillis = calendar.getTimeInMillis();
            setAlarm(timeInMillis);

            long timeDifference = timeInMillis - System.currentTimeMillis();

            long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60;

            @SuppressLint("DefaultLocale") String alarmInfo = String.format("闹钟设置成功！响铃时间为 %02d:%02d，距离现在还有 %02d 小时 %02d 分钟。",
                    timePicker.getHour(), timePicker.getMinute(), hours, minutes);
            alarmInfoTextView.setText(alarmInfo);
        });
    }

    @SuppressLint("MissingPermission")
    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            try {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
                Toast.makeText(this, "闹钟已设置！", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                Toast.makeText(this, "无法设置精确闹钟，缺少必要的权限", Toast.LENGTH_LONG).show();
            }
        }
    }



}
