package com.distillery.aaa.mazkekacontrol;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by AvivLaptop on 14/10/2017.
 */

public class Notifications extends Activity {
    public void noti (String temp, String sit){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.launcher);
        mBuilder.setContentTitle("Mazkeka");
        mBuilder.setContentText(sit + " - " +temp + "C");
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

    public void unoti(String temp, String sit) {
        NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(this);
        mBuilder2.setSmallIcon(R.drawable.launcher);
        mBuilder2.setContentTitle("Mazkeka");
        mBuilder2.setContentText(sit + " - " + temp + "C");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder2.build());
    }
}
