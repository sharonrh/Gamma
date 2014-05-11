package service;

import view.NotifikasiActivity;

import com.example.gamma.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyAlarm extends BroadcastReceiver
{

   @Override
   public void onReceive(Context context, Intent intent) {
           // Set the alarm here.
		   
		   System.out.println("notifikasi jalan....");
		   NotificationManager mNM;
	       mNM = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
	       // Set the icon, scrolling text and timestamp
	       int id = intent.getIntExtra("id", 0);
	       System.out.println("idku " + id);
	       Notification notification = new Notification(R.drawable.ic_launcher, "Test Alarm",
	       System.currentTimeMillis());
	       // The PendingIntent to launch our activity if the user selects this notification
	       PendingIntent contentIntent = PendingIntent.getActivity(context, id, new Intent(context, NotifikasiActivity.class), 0);
	       // Set the info for the views that show in the notification panel.
	       notification.setLatestEventInfo(context, context.getText(R.string.app_name), "This is a Test Alarm", contentIntent);
	       // Send the notification.
	       // We use a layout id because it is a unique number. We use it later to cancel.
	       mNM.notify(id, notification);
	   
	   
	   
   }
}
