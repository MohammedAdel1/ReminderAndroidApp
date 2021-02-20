package com.example.reminder.Data;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompatExtras;
import androidx.core.app.NotificationManagerCompat;

import com.example.reminder.R;
import com.example.reminder.UI.Activities.MainActivity;

public class ReminderReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.Preferences  , Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(MainActivity.Name , null);
        String budget = sharedPreferences.getString(MainActivity.Budget , null);
        NotificationHelper helper = new NotificationHelper(context);
        NotificationCompat.Builder nB = helper.getChannelNotification(name , budget);
        helper.getManager().notify(1,nB.build());

    }// end onReceive()
}// end Class
