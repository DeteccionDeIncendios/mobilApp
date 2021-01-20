package com.example.ianotifier;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SoundService extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.setVolume(80, 80);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        Toast.makeText(getApplicationContext(), "Reproducioendo sonido en segundo plano", Toast.LENGTH_SHORT).show();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
