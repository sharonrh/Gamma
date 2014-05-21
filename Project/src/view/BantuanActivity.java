package view;

import java.util.List;

import view.SettingFragment.Setting;
import view.SettingFragment.SettingArrayAdapter;
import view.SettingFragment.ViewHolder;

import com.example.gamma.R;

import controller.SettingController;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BantuanActivity extends Activity {
	
	ListView lv;
	SettingArrayAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_setting);
		
		String item [] = {"Setelah Mengisi Profil", "Penjelasan Fitur", "Mengenai Rekomendasi", "Navigasi di Aplikasi",
						  "Koneksi Internet", "Mengenai Notifikasi", "Informasi Lebih Lanjut"};
		
		lv = (ListView) findViewById(R.id.listView1);
		adapter = new SettingArrayAdapter(this, item);

		lv.setAdapter(adapter);

		// When item is tapped, toggle checked properties of CheckBox and
		// Planet.
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View item,
					int position, long id) {

				if (position == 0)
					bantuanPopupWindow(parent.getItemAtPosition(0).toString(), "");
				else if (position == 1)
					bantuanPopupWindow(parent.getItemAtPosition(1).toString(), "");
				else if (position == 2)
					bantuanPopupWindow(parent.getItemAtPosition(2).toString(), "");
				else if (position == 3)
					bantuanPopupWindow(parent.getItemAtPosition(3).toString(), "");
				else if (position == 4)
					bantuanPopupWindow(parent.getItemAtPosition(4).toString(), "");
				else if (position == 5)
					bantuanPopupWindow(parent.getItemAtPosition(5).toString(), "");
				else if (position == 6)
					bantuanPopupWindow(parent.getItemAtPosition(6).toString(), "");
			}
		});
	}
	
	static class ViewHolder {
		public TextView title;
		// public TextView subtitle;
	}

	class SettingArrayAdapter extends ArrayAdapter<String> {

		private LayoutInflater inflater;

		public SettingArrayAdapter(Context context, String [] settingList) {
			super(context, R.layout.simplerow, R.id.Title, settingList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String set = this.getItem(position);

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
			holder.title.setText(set);
			return convertView;
		}
	}
	
	public void bantuanPopupWindow(String title, String isi) {

		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.create();

		alertDialog.setTitle(title);
		LayoutInflater inflater = this.getLayoutInflater();

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
}