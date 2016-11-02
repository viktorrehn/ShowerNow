package com.first.akashshrivastava.showernow;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm running",
                Toast.LENGTH_LONG).show();

        Intent notificationIntent = new Intent(context, SignupActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

         NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.male_silhouette)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.male_silhouette))
                        .setContentTitle("Shower Now")
                        .setContentText("its time to shower")
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true);

// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(0, mBuilder.build());
    }



}
