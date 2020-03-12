package com.cse.cou.praptimoni.softwarefirmsbd;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        showMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    public  void  showMessage(String title,String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"my_notification")
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentText(message);
        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("ssssss", "onNewToken: "+s);
    }
}
