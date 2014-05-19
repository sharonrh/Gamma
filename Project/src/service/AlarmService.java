package service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class AlarmService {
	private Context context;
    private PendingIntent mAlarmSender;
    AlarmManager am;
    private long waktu;
    private int id;
    
    public AlarmService() {  
    }
    
    
    public void setWaktu(long time){
    	this.waktu = time;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    
    public void startAlarm(Context context, int id, long waktu){
        //Set the alarm to 10 seconds fromow
    	
    	Intent intent = new Intent(context, MyAlarm.class);
        intent.putExtra("id", id);
    	mAlarmSender = PendingIntent.getBroadcast(context, id, intent, 0); 

        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 10);
        c.add(Calendar.MINUTE, 0);
        c.add(Calendar.SECOND, 0);
      //  long firstTime = c.getTimeInMillis();
        // Schedule the alarm!
        System.out.println("notifikasi jalan");
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        System.out.println("notifikasi jalan");
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, waktu, AlarmManager.INTERVAL_DAY, mAlarmSender);
       // am.set(AlarmManager.RTC_WAKEUP, waktu, mAlarmSender);
        System.out.println("notifikasi jalan");
        
//        ComponentName receiver = new ComponentName(context, AlarmServiceReciever.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP); 
    }
    
    public void cancelAlarm(){
    	
    	if(am != null || mAlarmSender != null){
    		am.cancel(mAlarmSender);
    	}
    }
}
