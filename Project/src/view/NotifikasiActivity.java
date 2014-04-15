package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Notifikasi;

import service.NotifikasiService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.gamma.R;

import controller.NotifikasiController;

@SuppressLint("SimpleDateFormat")
public class NotifikasiActivity extends Activity {
	
	String str;
	Button tambahNotifikasiBtn;
	ListNotifikasiArrayAdapter adapter;
	NotifikasiController kontrol;
	ListView lv;
	List<Notifikasi> data = new ArrayList<Notifikasi>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifikasi);
		
		
		tambahNotifikasiBtn = (Button) findViewById(R.id.tmbhNtfkasiBtn);
		lv = (ListView) findViewById(R.id.listViewNotifikasi);
		kontrol = new NotifikasiController(getApplicationContext());
		
		data = kontrol.getListNotifikasi();
		adapter = new ListNotifikasiArrayAdapter(this, data);
		lv.setAdapter(adapter);
		
		tambahNotifikasiBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				AlertDialog alertDialog = new AlertDialog.Builder(
						NotifikasiActivity.this).create();

				// ListNotifikasi Dialog Title
				alertDialog.setTitle("Tambah Notifikasi");

				// ListNotifikasi Dialog Message
				 LayoutInflater inflater = getLayoutInflater();

				    // Inflate and set the layout for the dialog
				    // Pass null as the parent view because its going in the dialog layout
				 View v = inflater.inflate(R.layout.atur_notifikasi_layout, null);
				 alertDialog.setView(v);

				// ListNotifikasi Icon to Dialog
			//	alertDialog.setIcon(R.drawable.ic_launcher);
				 
				 final EditText tv = (EditText) v.findViewById(R.id.namaNotifikasiFields);
				 final TimePicker tp =(TimePicker) v.findViewById(R.id.timePicker1);
				 tp.setIs24HourView(true);
				 
				// ListNotifikasi OK Button
				 alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "batal", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							
						}
					});
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "simpan", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						Time t = new Time();
						t.setToNow();
						System.out.println(t.hour);
						System.out.println(t.minute);
						
						int jamSekarang = tp.getCurrentHour();
						int menitSekarang = tp.getCurrentMinute();
						
						Time nt = new Time();
						
						//nyimpen waktu sekarang
						nt.set(0, menitSekarang, jamSekarang, t.monthDay, t.month, t.year);
						
						long l = nt.toMillis(false);
						System.out.println("abc2");
						
						//nyimpen ke database
						System.out.println(kontrol.addNotifikasi(tv.getText().toString(),l , tv.getText().toString()));
						System.out.println(data);
						
						
						
						
						lv.setAdapter(adapter);
						recreate();
					}
				});
				
				// Showing Alert Message
				alertDialog.show();
			
			}
			
		});
		
		if(!data.isEmpty()){
			long l = data.get(data.size()-1).getWaktu();
			
			
			Date date = new Date(l);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String formatted = format.format(date);
			String[] str = formatted.split(":");
			
			int jam = Integer.parseInt(str[0]);
			int menit = Integer.parseInt(str[1]);
			startAlarm(jam, menit);
		}
		
		
		System.out.println("123" + data);
		
		
		
	}
	
	class ListNotifikasi {
		String title;
		String subTitle;

		public ListNotifikasi(String title, String subTitle) {
			super();
			this.title = title;
			this.subTitle = subTitle;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubTitle() {
			return subTitle;
		}

		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
		
		public String toString(){
			return title;
		}

	}

	/** Holds child views for one row. */
	class ListNotifikasiViewHolder {
		private TextView textView1;
		private TextView textView2;

		public ListNotifikasiViewHolder() {
		}

		public ListNotifikasiViewHolder(TextView textView1, TextView textView2) {
			super();
			this.textView1 = textView1;
			this.textView2 = textView2;
		}

		public TextView getTextView1() {
			return textView1;
		}

		public void setTextView1(TextView textView1) {
			this.textView1 = textView1;
		}

		public TextView getTextView2() {
			return textView2;
		}

		public void setTextView2(TextView textView2) {
			this.textView2 = textView2;
		}
	}

	class ListNotifikasiArrayAdapter extends ArrayAdapter<Notifikasi> {

		private LayoutInflater inflater;

		public ListNotifikasiArrayAdapter(Context context, List<Notifikasi> ListNotifikasiList) {
			super(context, R.layout.notifikasi_simplerow, R.id.Title, ListNotifikasiList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Nasabah to display
			Notifikasi set = (Notifikasi) this.getItem(position);

			// The child views in each row.
			TextView tv1;
			TextView tv2;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.notifikasi_simplerow, null);

				// Find the child views.
				tv1 = (TextView) convertView.findViewById(R.id.Title);
				tv2 = (TextView) convertView.findViewById(R.id.subTitle);

				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView.setTag(new ListNotifikasiViewHolder(tv1, tv2));

			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				ListNotifikasiViewHolder viewHolder = (ListNotifikasiViewHolder) convertView
						.getTag();
				tv1 = viewHolder.getTextView1();
				tv2 = viewHolder.getTextView2();
			}

			if (tv1 != null) {
				
				Date date = new Date(set.getWaktu());
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String formatted = format.format(date);
				tv1.setText(set.getNama());
				tv2.setText(formatted);
			}

			return convertView;
		}
	}

	private void startAlarm(int jam, int menit) {
	    AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
	    Time t = new Time();
	    t.setToNow();
	    Time nt = new Time();
	    nt.set(0, menit, jam, t.month, t.monthDay, t.year);
	    long when = nt.toMillis(false);         // notification time
	            Intent intent = new Intent(getApplicationContext(), NotifikasiService.class);
	            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
	            alarmManager.setExact(AlarmManager.RTC, when, pendingIntent);
	        }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.notifikasi, menu);
		return true;
	}

}
