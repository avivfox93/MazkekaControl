package com.distillery.aaa.mazkekacontrol;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by AvivLaptop on 14/10/2017.
 */

public class Notifications extends Activity {
    NotificationManager mNotificationManager;
    Context contx;

    public Notifications(NotificationManager mNotoficationManager, Context contx){
        this.mNotificationManager = mNotoficationManager;
        this.contx = contx;
    }
    public void noti (String temp, String sit){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(contx);
        mBuilder.setSmallIcon(R.drawable.launcher);
        mBuilder.setContentTitle("Mazkeka");
        mBuilder.setContentText(sit + " -  " + String.format(temp, "%.3f") + "C");
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void unoti(String temp, String sit) {
        NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(contx);
        mBuilder2.setSmallIcon(R.drawable.launcher);
        mBuilder2.setContentTitle("Mazkeka");
        mBuilder2.setContentText(sit + " -  " + String.format(temp, "%.3f") + "C");
        mNotificationManager.notify(2, mBuilder2.build());
    }
}
