package com.example.ifttt;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimeActivityForSilenceMode extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioManager mode2 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mode2.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
}
