package com.aprosoftech.myclass;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by CSB on 04/05/18.
 */

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,ShowNeta.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,(int)System.currentTimeMillis(),intent1,0);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Neta has been added")
                .setContentText("Some text here as well")
                .setSmallIcon(R.mipmap.add_neta)
                .setContentIntent(pendingIntent1)
                .setAutoCancel(true).build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(1001,notification);


    }
}
