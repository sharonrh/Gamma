package view;

import java.io.IOException;
import java.io.InputStream;

import model.Makanan;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

import controller.KatalogController;

public class DetailMakananActivity extends Activity {

	private ImageView bintang1, bintang2, bintang3, bintang4, bintang5;
	private Button rate;
	private TextView nama, kalori, karbo, protein, lemak, sodium, jenis,
			hewani, kacang, seafood;
	private RatingBar rating;
	private ImageView foto;
	private KatalogController con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_makanan);

		rate = (Button) findViewById(R.id.rateBtn);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			final String mystring = extras.getString("nama");

			con = new KatalogController(getApplicationContext());

			nama = (TextView) findViewById(R.id.namaDetailMakanan);
			kalori = (TextView) findViewById(R.id.kaloriDetailMakanan);
			karbo = (TextView) findViewById(R.id.karbohidrat);
			protein = (TextView) findViewById(R.id.protein);
			lemak = (TextView) findViewById(R.id.lemak);
			sodium = (TextView) findViewById(R.id.sodium);
			jenis = (TextView) findViewById(R.id.jenis);
			hewani = (TextView) findViewById(R.id.hewani);
			kacang = (TextView) findViewById(R.id.kacang);
			seafood = (TextView) findViewById(R.id.seafood);
			foto = (ImageView) findViewById(R.id.fotoDetailMakanan);
			rating = (RatingBar) findViewById(R.id.ratingBarDetail);

			nama.setText(mystring);
			Makanan m = con.getMakanan(mystring);

			Bitmap bm = null;
			try {
				bm = getBitmapFromAsset(m.getPathFoto());
				foto.setImageBitmap(bm);
			} catch (IOException e) {
				e.printStackTrace();
			}

			kalori.setText(m.getKalori() + " kal");
			karbo.setText(m.getKarbohidrat() + " gr");
			protein.setText(m.getProtein() + " gr");
			lemak.setText(m.getLemak() + " gr");
			sodium.setText(m.getNatrium() + " gr");
			jenis.setText(m.getJenisMakanan());

			String stat = m.isHewani() ? "Ya" : "Tidak";
			hewani.setText(stat);
			stat = m.isKacang() ? "Ya" : "Tidak";
			kacang.setText(stat);
			stat = m.isSeafood() ? "Ya" : "Tidak";
			seafood.setText(stat);
			rating.setRating(m.getRating());
			
			rate.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					con.updateRating(mystring, rating.getRating());
					finish();
					Toast.makeText(getApplicationContext(),
							"Rating sudah disimpan",
							Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("nomorFragment", "4");
					startActivity(i);
				}
			});
		}
	}

	public Bitmap getBitmapFromAsset(String strName) throws IOException {
		AssetManager assetManager = getResources().getAssets();
		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

}