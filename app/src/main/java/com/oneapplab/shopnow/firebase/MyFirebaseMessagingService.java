package com.oneapplab.shopnow.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.dashboard.DashboardActivity;

/**
 * Created by haider on 28-03-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
  //      Log.d(TAG, "From: " + remoteMessage.getFrom());
//       Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
      sendNotification(remoteMessage.getNotification().getBody());

       // method to receive from console
        //sendNotification(remoteMessage.getNotification().getBody());
      // sendNotification(remoteMessage.getData().put("","body"));


    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    public void sendNotification(String messageBody) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Do you currently have?").setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(new long[] {0, 1000, 200,1000 })
                 .setLights(Color.RED, 3000, 3000)
              .setTicker("New message from PayMe..")

                .setPriority(Notification.PRIORITY_MAX)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}