package view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import view.Utils;
import com.example.gamma.R;

import controller.SettingController;

//import android.support.v4.app.Fragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class SettingActivity extends Activity {

	ListView lv;
	TextView tv;
	SettingArrayAdapter adapter;
	SettingController kontrol;
	private PopupWindow popUpTema;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.fragment_setting);

		ArrayList<Setting> set = new ArrayList<Setting>();
		kontrol = new SettingController(this);

		set.add(new Setting("Notifikasi", "Atur Notifikasi"));
		set.add(new Setting("Tema", "Ganti Tema Aplikasi"));
		set.add(new Setting("Tentang", "Info Mengenai Pengembang"));

		lv = (ListView) findViewById(R.id.listView1);
		adapter = new SettingArrayAdapter(this, set);

		lv.setAdapter(adapter);

		// When item is tapped, toggle checked properties of CheckBox and
		// Planet.
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View item,
					int position, long id) {
				
				if(position == 0){
					kontrol.gantiHalaman(position);
				}
				else if (position == 1) {
					temaPopupWindow();
				}
				else {
					tentangPopupWindow();
				}
					

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	class Setting {
		String title;
		String subTitle;

		public Setting(String title, String subTitle) {
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

	}

	/** Holds child views for one row. */
	class SettingViewHolder {
		private TextView textView1;
		private TextView textView2;

		public SettingViewHolder() {
		}

		public SettingViewHolder(TextView textView1, TextView textView2) {
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

	class SettingArrayAdapter extends ArrayAdapter<Setting> {

		private LayoutInflater inflater;

		public SettingArrayAdapter(Context context, List<Setting> settingList) {
			super(context, R.layout.simplerow, R.id.Title, settingList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Nasabah to display
			Setting set = (Setting) this.getItem(position);

			// The child views in each row.
			TextView tv1;
			TextView tv2;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.simplerow, null);

				// Find the child views.
				tv1 = (TextView) convertView.findViewById(R.id.Title);
				tv2 = (TextView) convertView.findViewById(R.id.subTitle);

				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView.setTag(new SettingViewHolder(tv1, tv2));

			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				SettingViewHolder viewHolder = (SettingViewHolder) convertView
						.getTag();
				tv1 = viewHolder.getTextView1();
				tv2 = viewHolder.getTextView2();
			}

			if (tv1 != null) {
				tv1.setText(set.getTitle());
				tv2.setText(set.getSubTitle());
			}

			return convertView;
		}
	}

	RadioGroup rg;

	@SuppressWarnings("deprecation")
	public void temaPopupWindow() {

		AlertDialog alertDialog = new AlertDialog.Builder(
				SettingActivity.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Pilih Tema");

		// Setting Dialog Message
		 LayoutInflater inflater = getLayoutInflater();

		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		 
		 
		 final View v = inflater.inflate(R.layout.layout_tema, null);
		 alertDialog.setView(v);

		// Setting Icon to Dialog
		 
		 	
			

		// Setting OK Button
		alertDialog.setButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						RadioGroup rg = (RadioGroup) v.findViewById(R.id.pilihTema);
						
						//setContentView(popUpTema.getContentView());
						System.out.println("---------------id="+rg.getCheckedRadioButtonId());
						switch (rg.getCheckedRadioButtonId())
						//switch (popUpTema.getContentView().getId()) 
						{
							
						case R.id.holoDark:
							Utils.THEME="defaultTheme";
						    Utils.settingChanged=true;
						    startActivity(new Intent(SettingActivity.this,  MainActivity.class));
							break;
						case R.id.holoLight:
							Utils.THEME="Gray";
						    Utils.settingChanged=true;;
						    startActivity(new Intent(SettingActivity.this,  MainActivity.class));
							break;
						case R.id.deFault :
							Utils.THEME="Radial";
						    Utils.settingChanged=true;
						    startActivity(new Intent(SettingActivity.this,  MainActivity.class)); 
							break;
						default :
							  break;
						}
					}
				});
		// Showing Alert Message
		alertDialog.show();
	
	}
	
	public void tentangPopupWindow() {

		AlertDialog alertDialog = new AlertDialog.Builder(
				SettingActivity.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Tentang");

		// Setting Dialog Message
		 LayoutInflater inflater = getLayoutInflater();

		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		 alertDialog.setView(inflater.inflate(R.layout.activity_tentang, null));

		// Setting Icon to Dialog
	//	alertDialog.setIcon(R.drawable.ic_launcher);

		// Setting OK Button
		alertDialog.setButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {

					}
				});
		// Showing Alert Message
		alertDialog.show();
	
	}

}
