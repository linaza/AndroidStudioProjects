package com.me;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.TextView;
import java.util.Random;
public class NotificationReciever extends BroadcastReceiver {
    TextView note;
    MediaPlayer ring;
    public String Title , Text;
    public String str1="I Just Want To Say:";
    public String str2="جل من سواك يا قمري";
    String [] array = new String[]{
            "وهديتي وردة خبيتها بكتابي",
            "انا خوفي ياحبي لتكون بعدك حبي وبتهيالي نسيتك وانت مخبي بقلبي",
            "لما نكون سوا",
            "يوما ما",
            "الشوق يغنيلك يا تجيني يا حجيلك",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"};
    Random rand = new Random();
    int randomNum = 0 + rand.nextInt((25 - 1) + 1);
    NotificationReciever(){
        str2 = array[randomNum];
        this.Title = str1;
        this.Text = str2;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //building the notification!

        ring= MediaPlayer.create(context,R.raw.hi_sweetie);
        ring.start();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, Repeat_page.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_loyalty_black_24dp)
                .setContentTitle(str1)
                .setContentText(array[randomNum])
                .setAutoCancel(true);
        //choosing a random notification :-
        Title = str1;
        Text = str2;
        notificationManager.notify(100, builder.build());
    }
    public String noteTitle(){
        return Title;
    }
    public String noteText(){
        return Text;
    }

}
