package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Notifikasi;
import service.AlarmService;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gamma.R;

import controller.NotifikasiController;

@SuppressLint({ "SimpleDateFormat", "NewApi" })
public class NotifikasiActivity extends Activity {

	String str;
	Button tambahNotifikasiBtn;
	ListNotifikasiArrayAdapter adapter;
	NotifikasiController kontrol;
	ListView lv;
	String nama = "";
	AlarmService alrm;
	List<Notifikasi> data = new ArrayList<Notifikasi>();
	int id = 0;
	TimePicker tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifikasi);

		// tambahNotifikasiBtn = (Button) findViewById(R.id.tmbhNtfkasiBtn);
		lv = (ListView) findViewById(R.id.listViewNotifikasi);
		kontrol = new NotifikasiController(getApplicationContext());

		data = kontrol.getListNotifikasi();

		adapter = new ListNotifikasiArrayAdapter(this, data);
		lv.setAdapter(adapter);

		System.out.println(adapter.getItemId(0));
		System.out.println(adapter.getItemId(2));
		System.out.println(adapter.getCount());
		System.out.println(adapter.getItemViewType(2));
		System.out.println(adapter.getItemViewType(1));
		Notifikasi notif = adapter.getItem(0);
		System.out.println("selected: " + notif.isSelected());

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				++id;
				System.out.println("id"+id);
				showAlert(position);
//				lv.setAdapter(adapter);
//				recreate();

			}
		});

		int ii = 0;
		while (!data.isEmpty() && ii < data.size()) {
			long l = data.get(ii).getWaktu();
			ii++;
			Date date = new Date(l);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String formatted = format.format(date);
			String[] str = formatted.split(":");

			int jam = Integer.parseInt(str[0]);
			int menit = Integer.parseInt(str[1]);
			// startAlarm(jam, menit);
		}

		System.out.println("123" + data);

	}

	public void showAlert(int position) {

		// TODO Auto-generated method stub

		AlertDialog alertDialog = new AlertDialog.Builder(
				NotifikasiActivity.this).create();

		// ListNotifikasi Dialog Title
		String title = "";
		System.out.println(position);

		if (position == 0) {
			title = "Atur Waktu Sarapan";
			nama = "Sarapan";
			id=0;
		} else if (position == 1) {
			title = "Atur Waktu Makan Siang";
			nama = "Makan Siang";
			id = 1;
		} else if (position == 2) {
			title = "Atur Waktu Makan Malam";
			nama = "Makan Malam";
			id = 2;
		}
		else if (position == 3) {
				title = "Atur Waktu Snack";
				nama = "Snack";
				id = 3;
		}

		alertDialog.setTitle(title);

		// ListNotifikasi Dialog Message
		LayoutInflater inflater = getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		View v = inflater.inflate(R.layout.atur_notifikasi_layout, null);
		alertDialog.setView(v);

		// ListNotifikasi Icon to Dialog
		// alertDialog.setIcon(R.drawable.ic_launcher);
		
		tp = (TimePicker) v.findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		
		Time tn = new Time();
		tn.setToNow();
		tp.setCurrentHour(tn.hour);
		System.out.println(tp.is24HourView());
		
		final Calendar cal = Calendar.getInstance();

		// ListNotifikasi OK Button
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "batal",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "simpan",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Time t = new Time();
						t.setToNow();
						System.out.println(t.hour);
						System.out.println(t.minute);
						
						int jamSekarang = tp.getCurrentHour();
						int menitSekarang = tp.getCurrentMinute();

						// nyimpen waktu sekarang
						cal.set(t.year,t.month,t.monthDay, jamSekarang, menitSekarang, 0);

						long l = cal.getTimeInMillis();
						System.out.println("abc2");

						// nyimpen ke database
						System.out.println(nama);
						System.out.println(kontrol.updateNotifikasi(nama, l));
						System.out.println(data);

						lv.setAdapter(adapter);
						
						recreate();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

	class ListNotifikasi {
		String title;
		String subTitle;
		boolean selected;

		public ListNotifikasi(String title, String subTitle) {
			super();
			this.title = title;
			this.subTitle = subTitle;
		}

		private boolean isSelected() {
			return selected;
		}

		private void setSelected(boolean selected) {
			this.selected = selected;
		}
	}

	/** Holds child views for one row. */
	class ListNotifikasiViewHolder {
		private TextView textView1;
		private TextView timeTv;
		private Switch swNotif;
		
		
		public ListNotifikasiViewHolder(TextView textView1, TextView timeTv,
				Switch swNotif) {
			super();
			this.textView1 = textView1;
			this.timeTv = timeTv;
			this.swNotif = swNotif;
		}
		
		private TextView getTextView1() {
			return textView1;
		}
		private void setTextView1(TextView textView1) {
			this.textView1 = textView1;
		}
		private TextView getTimeTv() {
			return timeTv;
		}
		private void setTimeTv(TextView timeTv) {
			this.timeTv = timeTv;
		}
		private Switch getSwNotif() {
			return swNotif;
		}
		private void setSwNotif(Switch swNotif) {
			this.swNotif = swNotif;
		}
		
		
	}

	class ListNotifikasiArrayAdapter extends ArrayAdapter<Notifikasi> {

		private LayoutInflater inflater;
		List<Notifikasi> listNotifikasiList;

		public ListNotifikasiArrayAdapter(Context context,
				List<Notifikasi> listNotifikasiList) {
			super(context, R.layout.notifikasi_simplerow, R.id.Title,
					listNotifikasiList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			this.listNotifikasiList=listNotifikasiList;
			inflater = LayoutInflater.from(context);
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Nasabah to display
			final int posisi = position;
			System.out.println("position " + position);
			final Notifikasi set = (Notifikasi) this.getItem(position);

			// The child views in each row.
			TextView tv1;
			TextView tv2;
			Switch sw;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.notifikasi_simplerow,
						null);

				// Find the child views.
				tv1 = (TextView) convertView.findViewById(R.id.Title);
				tv2 = (TextView) convertView.findViewById(R.id.timeTV);
				sw = (Switch) convertView.findViewById(R.id.switch1);
				
				
				sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					 
		            public void onCheckedChanged(CompoundButton buttonView,
		                    boolean ischecked) {
		            	AlarmService myAlarm = new AlarmService();
		            	
		            	
		            	Date date = new Date(set.getWaktu());
						SimpleDateFormat format = new SimpleDateFormat("HH:mm");
						String formatted = format.format(date);
						String[] str = formatted.split(":");
						
						int jam = Integer.parseInt(str[0]);
						int menit = Integer.parseInt(str[1]);
						
		            	Calendar cal = Calendar.getInstance();
		            	cal.setTimeInMillis(set.getWaktu());
		            	
		            	System.out.println("posisi " + posisi);
		            	System.out.println("jam " + jam);
		            	System.out.println("menit " + menit);
		            	
		            	Calendar cal2 = Calendar.getInstance();
		            	cal2.set(Calendar.HOUR_OF_DAY, jam);
		            	cal2.set(Calendar.MINUTE, menit);
		            	
		            	
		                if (ischecked) {
		                	myAlarm.startAlarm(getApplicationContext(), posisi, cal2.getTimeInMillis());
		                	kontrol.updateNotifikasi(set.getNama(), cal2.getTimeInMillis(), true);
		                    
		                    
		                } else {
		                	kontrol.updateNotifikasi(set.getNama(), false);
		                	myAlarm.cancelAlarm();
		                    
		                }
		            }
		        });
				
				convertView.setTag(new ListNotifikasiViewHolder(tv1, tv2,sw));

			}
			// Reuse existing row view
			else {
				ListNotifikasiViewHolder viewHolder = (ListNotifikasiViewHolder) convertView
						.getTag();
				tv1 = viewHolder.getTextView1();
				tv2 = viewHolder.getTimeTv();
				sw = viewHolder.getSwNotif();
			}
			
			
			
			
			System.out.println(set.isSelected());

			if (tv1 != null) {
				
				Date date = new Date(set.getWaktu());
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String formatted = format.format(date);
				tv1.setText(set.getNama());
				tv2.setText(formatted + " WIB");
				sw.setChecked(set.isSelected());
			}

			return convertView;
		}
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.notifikasi, menu);
		return true;
	}
}
