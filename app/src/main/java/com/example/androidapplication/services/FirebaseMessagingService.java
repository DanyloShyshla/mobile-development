package com.example.androidapplication.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidapplication.presentation.activities.MainActivity;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import io.reactivex.annotations.NonNull;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String id = remoteMessage.getData().get("id");
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .setContentTitle("title")
                .setContentText("text")
                .setContentIntent(getClickActionIntent(id))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());

    }

    private PendingIntent getClickActionIntent(String id) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.putExtra("ID", id);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }


    private void createNotificationChannel() {


            CharSequence name = "CHANNEL_NAME";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

    }

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
    }
}

