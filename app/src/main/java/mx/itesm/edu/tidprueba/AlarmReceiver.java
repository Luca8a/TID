package mx.itesm.edu.tidprueba;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;



/**
 * Created by Luck on 5/2/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager  notificationManager;
    Notification myNotification;
    private final String myBlog ="Recordatorio";


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Alarm received!",Toast.LENGTH_LONG).show();
        Intent myIntent1 = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        myNotification = new NotificationCompat.Builder(context)
                .setContentTitle("TiD")
                .setContentText(myBlog)
                .setTicker("TiD!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
        notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(MY_NOTIFICATION_ID,myNotification);
    }
}
