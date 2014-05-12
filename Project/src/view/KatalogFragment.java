package view;

import java.util.List;

import model.Makanan;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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

		final List<Makanan> values = kontrol.getListMakanan();

		TextView pokok = (TextView) v.findViewById(R.id.makananPokokKatalog);
		TextView lauk = (TextView) v.findViewById(R.id.laukpaukKatalog);
		TextView sayur = (TextView) v.findViewById(R.id.sayurKatalog);
		TextView buah = (TextView) v.findViewById(R.id.buahKatalog);
		TextView minuman = (TextView) v.findViewById(R.id.minumanKatalog);
		TextView snack = (TextView) v.findViewById(R.id.snackKatalog);

		int[] ctJenis = kontrol.getJenisCount();
		pokok.setText("" + ctJenis[0]);
		lauk.setText("" + ctJenis[1]);
		sayur.setText("" + ctJenis[2]);
		buah.setText("" + ctJenis[3]);
		minuman.setText("" + ctJenis[4]);

		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(
				getActivity(), values);
		listview.setAdapter(adapter);

		// ubah tinggi listview katalog
		/**
		 * LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
		 * LayoutParams.MATCH_PARENT, (adapter.getCount() * 50));
		 * listview.setLayoutParams(mParam);
		 */

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), DetailMakananActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				intent.putExtra("nama", values.get(position).getNama());

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
			public RatingBar rating;
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
				viewHolder.rating = (RatingBar) rowView
						.findViewById(R.id.ratingBarList);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();

			Makanan m = names.get(position);
			holder.nama.setText(m.getNama());
			holder.kalori.setText(m.getKalori() + " kal");
			holder.rating.setRating(m.getRating());

			/**GridLayout layout = (GridLayout) getActivity().findViewById(
					R.id.kartuKatalog);

			String jenis = m.getJenisMakanan();
			if (jenis.equals("Pokok")) {
				 layout.setBackgroundResource((R.drawable.selector_card_background_makananpokok));
			} else if (jenis.equals("Buah")) {
				 layout.setBackgroundResource((R.drawable.selector_card_background_buah));
			} else if (jenis.equals("Sayuran")) {
				 layout.setBackgroundResource((R.drawable.selector_card_background_sayur));
			} else if (jenis.equals("Snack")) {
				//layout.setBackgroundDrawable((getResources().getDrawable(R.drawable.s)));
			} else if (jenis.equals("Lauk")) {
				 layout.setBackgroundResource((R.drawable.selector_card_background_laukpauk));
			} else { // minuman
				 layout.setBackgroundResource((R.drawable.selector_card_background_minuman));
			}*/

			return rowView;
		}
	}

}