package com.me;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import static android.content.ContentValues.TAG;
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String share_pref="shared";
    public static final String text="text";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //String s = remoteMessage.getNotification().getTitle() + "\n" + remoteMessage.getNotification().getBody();
        //String s="notification";
        if (!remoteMessage.getData().isEmpty()) {
            String s = remoteMessage.getNotification().getTitle() + "\n" + remoteMessage.getNotification().getBody();
            save(s);

            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        } else {
            String s = remoteMessage.getNotification().getTitle() + "\n" + remoteMessage.getNotification().getBody();
            save(s);
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        String s = remoteMessage.getNotification().getTitle() + "\n" + remoteMessage.getNotification().getBody();

        save(s);
    }



    //-----------------------------------------------------------------------------------------------------------------------
    private void showNotification(String Title, String Body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NotificationChannel.DEFAULT_CHANNEL_ID, "notification",
                    notificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("EDMT CHANEL");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_loyalty_black_24dp)
                .setContentTitle(Title)
                .setContentText(Body)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }
    //-----------------------------------------------------------------------------------------------------------------------
    //saving shared prefrences:
    public void save(String s){
        SharedPreferences sharedPreferences=getSharedPreferences(share_pref,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(text,s);
        editor.commit();

    }
    //-----------------------------------------------------------------------------------------------------------------------

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: \n ????????????????????????????????????????????????????????????????????????????????/" + token);
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(token, refreshedToken).apply();
    }
    //-----------------------------------------------------------------------------------------------------------------------



}