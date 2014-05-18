package view;

import java.util.List;

import model.Makanan;
import view.KatalogFragment.MyPerformanceArrayAdapter;
import view.KatalogFragment.MyPerformanceArrayAdapter.ViewHolder;

import com.example.gamma.R;

import controller.KatalogController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class JenisMakananActivity extends Activity {
	
	private ImageView gambar;
	private TextView jenis, jumlah;
	private ListView listJenis;
	private Bundle extras;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jenis_makanan);
		
		KatalogController kontrol = new KatalogController(getApplicationContext());

		final List<Makanan> values = kontrol.getListMakanan();
		int[] ctJenis = kontrol.getJenisCount();
		listJenis = (ListView) findViewById(R.id.listJenisMakanan);
		
		gambar = (ImageView) findViewById(R.id.gambarJenis);
		jenis = (TextView) findViewById(R.id.namaJenis);
		jumlah = (TextView) findViewById(R.id.jumlahJenis);
		extras = getIntent().getExtras();
		final String mystring = extras.getString("jenis");
		
		if(mystring.equalsIgnoreCase("pokok")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_makananpokok);
			gambar.setImageResource(R.drawable.ikon_pokok);
			jenis.setText("Makanan Pokok");
			jenis.setBackgroundResource(R.drawable.layer_card_background_makananpokok);
			jumlah.setText("" + ctJenis[0]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_makananpokok);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Pokok"));
			listJenis.setAdapter(adapter);
		}
		else if(mystring.equalsIgnoreCase("lauk")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_laukpauk);
			gambar.setImageResource(R.drawable.ikon_lauk);
			jenis.setText("Lauk Pauk");
			jenis.setBackgroundResource(R.drawable.layer_card_background_laukpauk);
			jumlah.setText("" + ctJenis[1]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_laukpauk);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Lauk"));
			listJenis.setAdapter(adapter);
		}
		else if(mystring.equalsIgnoreCase("sayur")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_sayur);
			gambar.setImageResource(R.drawable.ikon_sayur);
			jenis.setText("Sayur Sayuran");
			jenis.setBackgroundResource(R.drawable.layer_card_background_sayur);
			jumlah.setText("" + ctJenis[2]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_sayur);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Sayur"));
			listJenis.setAdapter(adapter);
		}
		else if(mystring.equalsIgnoreCase("buah")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_buah);
			gambar.setImageResource(R.drawable.ikon_buah);
			jenis.setText("Buah Buahan");
			jenis.setBackgroundResource(R.drawable.layer_card_background_buah);
			jumlah.setText("" + ctJenis[3]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_buah);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Buah"));
			listJenis.setAdapter(adapter);
		}
		else if(mystring.equalsIgnoreCase("minum")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_minuman);
			gambar.setImageResource(R.drawable.ikon_minuman);
			jenis.setText("Minuman");
			jenis.setBackgroundResource(R.drawable.layer_card_background_minuman);
			jumlah.setText("" + ctJenis[4]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_minuman);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Minuman"));
			listJenis.setAdapter(adapter);
		}
		else if(mystring.equalsIgnoreCase("snack")){
			gambar.setBackgroundResource(R.drawable.layer_card_background_snack);
			gambar.setImageResource(R.drawable.ikon_snack);
			jenis.setText("Snack");
			jenis.setBackgroundResource(R.drawable.layer_card_background_snack);
			jumlah.setText("" + ctJenis[5]);
			jumlah.setBackgroundResource(R.drawable.layer_card_background_snack);
			MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(this, kontrol.getMakananPerJenis("Snack"));
			listJenis.setAdapter(adapter);
		}
		
		
		
		listJenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(), DetailMakananActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				intent.putExtra("nama", values.get(position).getNama());

				startActivity(intent);
			}
		});
		
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