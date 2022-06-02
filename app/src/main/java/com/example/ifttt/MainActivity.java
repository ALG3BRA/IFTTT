package com.example.ifttt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button setTime;

    Button setVibrationMode;
    Button setNoSoundMode;
    int soundMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTime.findViewById(R.id.Метка);

        setVibrationMode.findViewById(R.id.Вибро);
        setNoSoundMode.findViewById(R.id.Не_беспокоить);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Вибро:
                        soundMode = 1;
                        break;
                    case R.id.Не_беспокоить:
                        soundMode = 2;
                        break;
                }
            }
        };

        setVibrationMode.setOnClickListener(onClickListener);
        setNoSoundMode.setOnClickListener(onClickListener);

        setTime.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .build();

            materialTimePicker.addOnPositiveButtonClickListener(view -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                AlarmManager.AlarmClockInfo alarmClockInfo =
                        new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getTimeInfoPendingIntent());
                alarmManager.setAlarmClock(alarmClockInfo, getTimeActionPendingIntent());
            });

            materialTimePicker.show(getSupportFragmentManager(), "tag_picker");

        });
    }

    private PendingIntent getTimeInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(this, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getTimeActionPendingIntent() {
        if (soundMode == 1) {
            Intent intent = new Intent(this, TimeActivityForVibrationMode.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        } else if (soundMode == 2) {
            Intent intent = new Intent(this, TimeActivityForSilenceMode.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else { return null; }
    }
}