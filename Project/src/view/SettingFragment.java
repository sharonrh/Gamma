package view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

import controller.ResetController;
import controller.SettingController;
import dao.DatabaseHandler;

public class SettingFragment extends PreferenceFragment {

	ListView lv;
	TextView tv;
	SettingArrayAdapter adapter;
	SettingController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View v = inflater.inflate(R.layout.fragment_setting, container, false);

		ArrayList<Setting> set = new ArrayList<Setting>();
		kontrol = new SettingController(getActivity());

		set.add(new Setting("Notifikasi", "Atur Notifikasi"));
		set.add(new Setting("Reset Progress", "Atur Notifikasi"));
		set.add(new Setting("Tentang", "Info Mengenai Pengembang"));
		set.add(new Setting("Kredit", "Atur Notifikasi"));
		set.add(new Setting("Bantuan", ""));

		lv = (ListView) v.findViewById(R.id.listView1);
		adapter = new SettingArrayAdapter(getActivity(), set);

		lv.setAdapter(adapter);

		// When item is tapped, toggle checked properties of CheckBox and
		// Planet.
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View item,
					int position, long id) {

				if (position == 0)
					kontrol.gantiHalaman(position);
				// } else if (position == 3) {
				// temaPopupWindow();
				else if (position == 1)
					showAlertReset();
				else if (position == 2)
					tentangPopupWindow();
				else if (position == 3)
					kreditPopupWindow();
				else if (position == 4)
					kontrol.gantiHalaman(4);

			}
		});
		return v;
	}

	class Setting {
		String title;
		String subTitle;

		public Setting(String title, String subTitle) {
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

	static class ViewHolder {
		public TextView title;
		// public TextView subtitle;
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
			Setting set = (Setting) this.getItem(position);

			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.setting_simplerow, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.Title);
				// viewHolder.subtitle = (TextView) convertView
				// .findViewById(R.id.subTitle);

				convertView.setTag(viewHolder);

			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.title.setText(set.getTitle());
			return convertView;
		}
	}

	RadioGroup rg;

	public void showAlertReset() {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		LayoutInflater inflater = getActivity().getLayoutInflater();

		View v = inflater.inflate(R.layout.popup_konfirmasi_reset, null);
		alertDialog.setView(v);

		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ya",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Context c = getActivity().getApplicationContext();
						ResetController rc = new ResetController(c);
						rc.reset(c);
						Toast.makeText(c, R.string.sukses_reset, Toast.LENGTH_SHORT).show();
						
						SharedPreferences prefs = getActivity().getSharedPreferences("com.example.gamma", getActivity().MODE_PRIVATE);
						prefs.edit().putBoolean("firstrun", true).commit();
						
						new Handler().postDelayed(new Runnable() {

							@Override
							public void run() {
								getActivity().finish();
								Intent i = new Intent(getActivity().getApplicationContext(),
										MainActivity.class);
								startActivity(i);
							}
						}, 2000);											
					}
				});
		alertDialog.show();
	}

	public void tentangPopupWindow() {

		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Tentang");
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		alertDialog.setView(inflater.inflate(R.layout.activity_tentang, null));

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.show();

	}

	public void kreditPopupWindow() {

		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Kredit");
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		alertDialog.setView(inflater.inflate(R.layout.activity_kredit, null));

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.show();
	}

}
