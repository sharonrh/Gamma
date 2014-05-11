package service;

import java.util.Calendar;
import java.util.List;

import model.Notifikasi;
import controller.NotifikasiController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class AlarmServiceReciever extends BroadcastReceiver {
	
    AlarmService alarm = new AlarmService();
    @SuppressWarnings("deprecation")
	@Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
        	NotifikasiController kontrol = new NotifikasiController(context);
            List<Notifikasi> list = kontrol.getListNotifikasi();
            
            long waktuSarapan = list.get(0).getWaktu();
            long waktuSiang = list.get(1).getWaktu();
            long waktuMalam = list.get(2).getWaktu();
            long snack = list.get(3).getWaktu();
            
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            
            //waktu sarapan
            cal.setTimeInMillis(waktuSarapan);
            
            cal2.set(Calendar.HOUR_OF_DAY, cal.getTime().getHours());
            cal2.set(Calendar.MINUTE, cal.getTime().getHours());
            
        	alarm.startAlarm(context, 0, cal2.getTimeInMillis());
        	
        	
        	//makan siang
            cal.setTimeInMillis(waktuSiang);
            
            cal2.set(Calendar.HOUR_OF_DAY, cal.getTime().getHours());
            cal2.set(Calendar.MINUTE, cal.getTime().getHours());
            
        	alarm.startAlarm(context, 1, cal2.getTimeInMillis());
        	
        	
        	//makan malam
        	cal.setTimeInMillis(waktuMalam);
            
            cal2.set(Calendar.HOUR_OF_DAY, cal.getTime().getHours());
            cal2.set(Calendar.MINUTE, cal.getTime().getHours());
            
        	alarm.startAlarm(context, 2, cal2.getTimeInMillis());
        	
        	
        	//snack
        	cal.setTimeInMillis(snack);
            
            cal2.set(Calendar.HOUR_OF_DAY, cal.getTime().getHours());
            cal2.set(Calendar.MINUTE, cal.getTime().getHours());
            
        	alarm.startAlarm(context, 3, cal2.getTimeInMillis());
        	
        }
    }	
}
