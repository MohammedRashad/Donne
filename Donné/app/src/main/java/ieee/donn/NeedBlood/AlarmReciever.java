package ieee.donn.NeedBlood;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import ieee.donn.Main.MainActivity;

/**
 * .
 * Created by rashad on 6/9/16.
 * .
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();

        PendingIntent pi = PendingIntent.getActivity(arg0, 0, new Intent(arg0, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(arg0)
                .setTicker("NeedBlood -- Donation Time")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Donation Time")
                .setContentText("it's time to go to the nearest hospital and give your blood someone in need :D")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);


    }

}