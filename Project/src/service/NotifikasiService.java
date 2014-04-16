package service;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Notifikasi;
import view.MainActivity;

import com.example.gamma.R;

import controller.NotifikasiController;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class NotifikasiService extends Service {
	private static  int NOTIFY_ME_ID=1337;
	private static final String TAG = "MyService";
	private NotifikasiController kontrol;
	List<Notifikasi> notifList;
	
	public NotifikasiService() {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		
		kontrol = new NotifikasiController(getApplicationContext());
		Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		notifList = kontrol.getListNotifikasi();
		
//		for (int ii=0; ii<notifList.size(); ii++){
//			Time t = new Time();
//			t.setToNow();
//			long l = notifList.get(ii).getWaktu();
//			
//			
//			Date date = new Date(l);
//			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//			String formatted = format.format(date);
//			String[] str = formatted.split(":");
//			
//			int jam = Integer.parseInt(str[0]);
//			int menit = Integer.parseInt(str[1]);
//			
//			Time nt = new Time();
//			nt.set(0, menit, jam, t.monthDay, t.month, t.year);
//			
//			
//		}
		showNotification();
		

	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");	
		System.out.println("afifun");
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
	
	public void showNotification(){

		// TODO Auto-generated method stub
		 
        final NotificationManager mgr=
            (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.gamma,
                                                        "Android Example Status message!",
                                                        System.currentTimeMillis());
         
        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(getApplicationContext(), 0,
                                                new Intent(getApplicationContext(), MainActivity.class),
                                                0);
         
        note.setLatestEventInfo(getApplicationContext(), "Android Example Notification Title",
                                "This is the android example notification message", i);
         
        //After uncomment this line you will see number of notification arrived
        //note.number=2;
        mgr.notify(++NOTIFY_ME_ID, note);
	
	}


}
