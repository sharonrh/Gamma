package view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gamma.R;

public class HalamanUtama extends Activity {
	ImageButton profil;
	ImageButton rekomendasi;
	ImageButton statistik;
	ImageButton katalog;
	ImageButton achievement;
	ImageButton pengaturan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_halaman_utama);

		profil = (ImageButton) findViewById(R.id.profil);
		rekomendasi = (ImageButton) findViewById(R.id.rekomendasi);
		statistik = (ImageButton) findViewById(R.id.statistik);
		katalog = (ImageButton) findViewById(R.id.katalog);
		achievement = (ImageButton) findViewById(R.id.achievement);
		pengaturan = (ImageButton) findViewById(R.id.pengaturan);

		profil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("nomorFragment", 1);

				startActivity(i);
			}
		});

		rekomendasi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("nomorFragment", 2);

				startActivity(i);
			}
		});

		statistik.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("nomorFragment", 3);
				startActivity(i);
			}
		});
		
		katalog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("nomorFragment", 4);
				startActivity(i);
			}
		});

		pengaturan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						SettingFragment.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.halaman_utama, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
