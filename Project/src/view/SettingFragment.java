package view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gamma.R;

import controller.SettingController;

public class SettingFragment extends Fragment {

	ListView lv;
	TextView tv;
	SettingArrayAdapter adapter;
	SettingController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(getActivity());

		View v = inflater.inflate(R.layout.fragment_setting, container, false);

		ArrayList<Setting> set = new ArrayList<Setting>();
		kontrol = new SettingController(getActivity());

		set.add(new Setting("Notifikasi", "Atur Notifikasi"));
		set.add(new Setting("Tema", "Ganti Tema Aplikasi"));
		set.add(new Setting("Tentang", "Info Mengenai Pengembang"));

		lv = (ListView) v.findViewById(R.id.listView1);
		adapter = new SettingArrayAdapter(getActivity(), set);

		lv.setAdapter(adapter);

		// When item is tapped, toggle checked properties of CheckBox and
		// Planet.
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View item,
					int position, long id) {

				if (position == 0) {
					kontrol.gantiHalaman(position);
				} else if (position == 1) {
					temaPopupWindow();
				} else {
					tentangPopupWindow();
				}

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
		public TextView subtitle;
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
				convertView = inflater.inflate(R.layout.simplerow, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.Title);
				viewHolder.subtitle = (TextView) convertView
						.findViewById(R.id.subTitle);

				convertView.setTag(viewHolder);

			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.title.setText(set.getTitle());
			holder.subtitle.setText(set.getSubTitle());

			return convertView;
		}
	}

	RadioGroup rg;

	@SuppressWarnings("deprecation")
	public void temaPopupWindow() {

		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Pilih Tema");
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		final View v = inflater.inflate(R.layout.layout_tema, null);
		alertDialog.setView(v);

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				RadioGroup rg = (RadioGroup) v.findViewById(R.id.pilihTema);

				switch (rg.getCheckedRadioButtonId()) {

				case R.id.holoDark:
					Utils.THEME = "defaultTheme";
					Utils.settingChanged = true;
					startActivity(new Intent(getActivity(), MainActivity.class));
					break;
				case R.id.holoLight:
					Utils.THEME = "Gray";
					Utils.settingChanged = true;
					startActivity(new Intent(getActivity(), MainActivity.class));
					break;
				case R.id.deFault:
					Utils.THEME = "Radial";
					Utils.settingChanged = true;
					startActivity(new Intent(getActivity(), MainActivity.class));
					break;
				default:
					break;
				}
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

}
