package service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmService {
	private Context context;
    private PendingIntent mAlarmSender;
    public AlarmService(Context context) {
        this.context = context;
        
    }

    public void startAlarm(long time, int id){
        //Set the alarm to 10 seconds fromow

    	mAlarmSender = PendingIntent.getBroadcast(context, id, new Intent(context, MyAlarm.class), 0);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 10);
        c.add(Calendar.MINUTE, 0);
        c.add(Calendar.SECOND, 0);
      //  long firstTime = c.getTimeInMillis();
        // Schedule the alarm!
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time, mAlarmSender);
    }
}
