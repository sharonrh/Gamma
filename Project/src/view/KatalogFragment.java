package view;

import java.util.List;

import model.Makanan;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamma.R;

import controller.KatalogController;

public class KatalogFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_katalog, container, false);
		ListView listview = (ListView) v.findViewById(R.id.listKatalog);

		KatalogController kontrol = new KatalogController(getActivity()
				.getApplicationContext());

		List<Makanan> values = kontrol.getListMakanan();

		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(
				getActivity(), values);
		listview.setAdapter(adapter);

		// ubah tinggi listview katalog
		LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (adapter.getCount() * 40));
		listview.setLayoutParams(mParam);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// you might want to use 'view' here
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), DetailMakananActivity.class);

				// passing nama makanan dari katalog ke detail makanan
				SharedPreferences spre = getActivity().getApplicationContext()
						.getSharedPreferences("Your prefName",
								Context.MODE_PRIVATE);
				SharedPreferences.Editor prefEditor = spre.edit();
				prefEditor.putString("key", parent.getItemAtPosition(position)
						.toString());
				prefEditor.commit();

				startActivity(intent);
			}
		});

		return v;
	}

	class MyPerformanceArrayAdapter extends ArrayAdapter<Makanan> {
		private final Activity context;
		private final List<Makanan> names;

		class ViewHolder {
			public TextView nama;
			public TextView kalori;
		}

		public MyPerformanceArrayAdapter(Activity context, List<Makanan> names) {
			super(context, R.layout.list_katalog, names);
			this.context = context;
			this.names = names;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			// reuse views
			if (rowView == null) {
				LayoutInflater inflater = context.getLayoutInflater();
				rowView = inflater.inflate(R.layout.list_katalog, null);
				// configure view holder
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.nama = (TextView) rowView
						.findViewById(R.id.makananKatalog);
				viewHolder.kalori = (TextView) rowView
						.findViewById(R.id.kaloriKatalog);

				rowView.setTag(viewHolder);
			}

			// fill data
			ViewHolder holder = (ViewHolder) rowView.getTag();
			Makanan m = names.get(position);
			holder.nama.setText(m.getNama());
			holder.kalori.setText(m.getKalori()+" kal");
			return rowView;
		}
	}

}