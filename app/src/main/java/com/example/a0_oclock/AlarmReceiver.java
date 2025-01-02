package com.example.a0_oclock;

import static android.content.Intent.getIntent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "example_channel_id";
    private static final int NOTIFICATION_ID = 1001;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent iter = new Intent(context,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, iter, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        ArrayList<String> names = intent.getStringArrayListExtra("EXTRA_NAMES");
        int receivedValue = intent.getIntExtra("daydiff", 0);


//        StringBuilder message = new StringBuilder();
//        int i = 0;
//        int n = names.size();
//        while(i<n){
//            message.append(names.get(i));
//            if(i != n - 1 ){
//                message.append(" , ");
//            }
//            i++;
//        }
//
//        String result ;
//        if (receivedValue == 1){
//            String ans = message.toString() + " have birthday tomorrow" ;
//            result = "" + ans;
//        }
//
//        else if(receivedValue == 2){
//            String ans = message.toString() + " have birthday after 2 days" ;
//            result = "" + ans;
//        }
//
//        else if(receivedValue == 7){
//            String ans = message.toString() + " have birthday after one week" ;
//            result = "" + ans;
//        }
//
//        else if(receivedValue == 15){
//            String ans = message.toString() + " have birthday after 15 days" ;
//            result = "" + ans;
//        }
//
//        else if(receivedValue == 30){
//            String ans = message.toString() + " have birthday after 1 month" ;
//            result = "" + ans;
//        }
//
//        else {
//            String ans = message.toString() + " have birthday after + " + receivedValue + " days" ;
//            result = "" + ans;
//        }
//
//        String ans = message.toString() + " have birthday tomorrow" ;
//        ArrayList<String> ans = new ArrayList<>() ;
//        ans = TimeActivity.storeInArrayList(receivedValue);
//        String result = ans.get(0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the notification channel (for Android O and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Example Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is an example notification channel");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent repeatingIntent = new Intent(context, Repeating_activity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Use PendingIntent.FLAG_IMMUTABLE if targeting Android 12 or higher
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification title")
                .setContentText("Great Bro")
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

//        if(names.size()!=0){
//            if (notificationManager != null) {
//                notificationManager.notify(NOTIFICATION_ID, builder.build());
//            }
//        }
    }
}
