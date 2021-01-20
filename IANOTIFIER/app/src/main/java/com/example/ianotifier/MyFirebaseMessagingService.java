package com.example.ianotifier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    static private String TAG = "FCN-SERVICE";
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.e(TAG, "Mensaje recibido de => "+from);

        if(remoteMessage.getNotification() != null){
            Log.e(TAG, "Titlulo => "+ remoteMessage.getNotification().getTitle());
            Log.e(TAG, "Body => "+ remoteMessage.getNotification().getBody());
        }

        if(remoteMessage.getData().size() > 0){
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String color = remoteMessage.getData().get("color");

            Log.e(TAG, "Titlulo data => "+ remoteMessage.getData().get("title"));
            Log.e(TAG, "Body data => "+ remoteMessage.getData().get("body"));
            Log.e(TAG, "color data => "+ remoteMessage.getData().get("color"));

            this.showNotification(title, body, color);

            Intent intent = new Intent(getApplicationContext(), SoundService.class);
            startService(intent);

        }
    }

    private void showNotification(String title, String body, String color){
        String id = "message";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(this.clickNotif())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentText(body)
                .setLights(Color.WHITE, 2000, 3000)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentInfo("Nuevo");
        Random random = new Random();
        int idNotif = random.nextInt(8000);
        assert nm != null;
        nm.notify(idNotif, builder.build());

    }

    private PendingIntent clickNotif(){
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.putExtra("color", "rojo");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, 0, nf, 0);
    }
}
