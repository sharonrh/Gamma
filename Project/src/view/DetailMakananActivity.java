package view;

import model.Makanan;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamma.R;

import controller.KatalogController;

public class DetailMakananActivity extends Activity {

	private ImageView bintang1, bintang2, bintang3, bintang4, bintang5;
	private Button rate;
	private TextView nama, kalori, karbo, protein, lemak, sodium, jenis,
			hewani, kacang, seafood;
	private ImageView foto;
	private KatalogController kontrol;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_detail_makanan);

		bintang1 = (ImageView) findViewById(R.id.bintang1Detail);
		bintang2 = (ImageView) findViewById(R.id.bintang2Detail);
		bintang3 = (ImageView) findViewById(R.id.bintang3Detail);
		bintang4 = (ImageView) findViewById(R.id.bintang4Detail);
		bintang5 = (ImageView) findViewById(R.id.bintang5Detail);

		rate = (Button) findViewById(R.id.rateBtn);

		// SharedPreferences spre = this.getSharedPreferences("Your prefName",
		// Context.MODE_PRIVATE);
		// String mystring = spre.getString("key", "");

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String mystring = extras.getString("nama");

			kontrol = new KatalogController(getApplicationContext());

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

			nama.setText(mystring);

			Makanan m = kontrol.getMakanan(mystring);
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

			// byte[] decodedString = Base64.decode(m.getPathFoto(),
			// Base64.DEFAULT);
			// Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
			// 0,
			// decodedString.length);
			// foto.setImageBitmap(decodedByte);

			bintang1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bintang2.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang3.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang4.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang5.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
				}
			});

			bintang2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bintang2.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang3.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang4.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang5.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
				}
			});

			bintang3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bintang2.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang3.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang4.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
					bintang5.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
				}
			});

			bintang4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bintang2.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang3.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang4.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang5.setImageDrawable(getResources().getDrawable(
							R.drawable.ic_action_content_edit));
				}
			});

			bintang5.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bintang2.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang3.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang4.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
					bintang5.setImageDrawable(getResources().getDrawable(
							R.drawable.achievement));
				}
			});
		}
	}
}