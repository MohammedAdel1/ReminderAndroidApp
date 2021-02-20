package com.example.reminder.Data;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.reminder.R;

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNEL_ID = "channelId";
    public static final String CHANNEL_NAME = "Events Reminders";
    private NotificationManager notificationManager;


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager (){
        if (notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification (String eventName , String eventBudget){
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Reminder")
                .setContentText("Don't miss out your " + eventName + " event \n" + "Remember you have to save " + eventBudget
                 + " for that event")
                .setSmallIcon(R.drawable.ic_baseline_access_time_24);

    }
}
