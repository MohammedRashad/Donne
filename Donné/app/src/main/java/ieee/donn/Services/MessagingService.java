package ieee.donn.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ieee.donn.Main.ConnectUsers;
import ieee.donn.Main.MainActivity;
import ieee.donn.R;

import static android.media.RingtoneManager.getDefaultUri;

/**
 * Created by rashad on 11/21/16.
 */
public class MessagingService extends FirebaseMessagingService {


    SharedPreferences.Editor edit;
    SharedPreferences spf;

    private static final String TAG = "FirebaseMessageService";
    private static final String ACTION_CONNECT_USERS = "ieee.donn.CONNECT_USERS";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        //
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        String message = remoteMessage.getData().get("message");
        Log.d(TAG,"Message: "+message);

        save("message", message);

        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        String bla = remoteMessage.getNotification().getBody();

        sendNotification("" + remoteMessage.getData(), bla);

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(String messageBody, String bla) {

        int requestID = (int) System.currentTimeMillis();
        int NOTIFICATION_ID = createID();

        Intent intent = new Intent(this, ConnectUsers.class);
        intent.setAction(ACTION_CONNECT_USERS);
        intent.putExtra("data",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //String channelId = getString(R.string.default_notification_channel_id);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Blood Needed")
                        .setContentText(bla)
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(100 /* ID of notification */, notificationBuilder.build());

    }


    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(this);
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }
}

