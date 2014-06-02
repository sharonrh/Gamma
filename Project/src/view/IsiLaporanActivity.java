package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.Laporan;
import model.Pengguna;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

import controller.LaporanController;
import controller.ProfilController;

public class IsiLaporanActivity extends Activity {

	private EditText beratField;
	private EditText tinggiField;
	private TextView beratLalu;
	private TextView tinggiLalu;
	private TextView tglHariLalu;
	private TextView tglHariIni;
	private TextView title;
	private Button simpanBtn;
	private Button batalBtn;
	private boolean cek = false;
	private String pesan = "";
	private double lastBerat = 0;
	private double lastTinggi = 0;
	private int tglLalu = 1;
	private int selisihHari = 0;
	private ProfilController profil;
	private Pengguna pengguna;
	private LaporanController kontrol;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_isilaporan);
		kontrol = new LaporanController(getApplicationContext());
		profil = new ProfilController(getApplicationContext());
		pengguna = profil.getProfil();
		
		
		beratField = (EditText) findViewById(R.id.beratIsiLaporan);
		tinggiField = (EditText) findViewById(R.id.tinggiIsiLaporan);
		tglHariLalu = (TextView) findViewById(R.id.tanggalHariSebelumnya);
		tglHariIni = (TextView) findViewById(R.id.tanggalHariIni);
		title = (TextView) findViewById(R.id.titleLaporan);
		simpanBtn = (Button) findViewById(R.id.button1);
		batalBtn = (Button) findViewById(R.id.button2);
		beratLalu = (TextView) findViewById(R.id.beratLaluIsiLaporan);
		tinggiLalu = (TextView) findViewById(R.id.tinggiLaluIsiLaporan);

		Laporan laporanTerbaru = kontrol.getLaporanTerbaru();
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String formatted = format.format(date);
		tglHariIni.setText("Data untuk tanggal " + formatted);
		tglHariLalu.setText("Data Sebelumnya (" + selisihHari
				+ " hari yang lalu)");
		
		Intent i = getIntent();
		System.out.println("intent " + i);
		Double berat = i.getDoubleExtra("berat", 0.0);
		Double tinggi = i.getDoubleExtra("tinggi", 0.0);
		
		if(berat != 0.0 && tinggi != 0.0){
			beratField.setText("" + berat);
			tinggiField.setText("" + tinggi);
			title.setText("Edit Laporan");
		}
		
		if (laporanTerbaru != null) {	
			lastBerat = laporanTerbaru.getBeratBadan();
			lastTinggi = laporanTerbaru.getTinggiBadan();
			
			System.out.println("'" + lastBerat);
			System.out.println("'" + lastTinggi);

			Calendar cal = Calendar.getInstance();

			long waktuLalu = laporanTerbaru.getWaktu();

			cal.setTimeInMillis(waktuLalu);
			tglLalu = cal.DAY_OF_YEAR;

			beratLalu.setText(lastBerat + " kg");
			tinggiLalu.setText(lastTinggi + " cm");	
		}
		else {
			
			
			beratLalu.setText(pengguna.getBerat() + " kg");
			tinggiLalu.setText(pengguna.getTinggi() + " cm");
			
			lastBerat = pengguna.getBerat();
			lastTinggi = pengguna.getTinggi();
		}

		simpanBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String berat = beratField.getText().toString();
				String tinggi = tinggiField.getText().toString();

				cek = false;

				if (validasiInput(berat, tinggi)) {
					cek = kontrol.addLaporan(berat, tinggi);
				} else {
					Toast.makeText(getApplicationContext(), pesan + ".",
							Toast.LENGTH_LONG).show();
					pesan = "";
				}

				if (cek) {
					Toast.makeText(getApplicationContext(),
							"Laporan berhasil disimpan", Toast.LENGTH_LONG)
							.show();
					beratLalu.setText(berat + " kg");
					tinggiLalu.setText(tinggi + " cm");
					
					pengguna.setBerat(Double.parseDouble(berat));
					pengguna.setTinggi(Double.parseDouble(tinggi));
					
					finish();
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					i.putExtra("nomorFragment", "3");

					startActivity(i);

				} else {
					Toast.makeText(getApplicationContext(),
							"Laporan gagal disimpan", Toast.LENGTH_LONG).show();
				}
				
				
			}
		});

		batalBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public boolean validasiInput(String berat, String tinggi) {

		ArrayList<String> list = new ArrayList<String>();

		Calendar kalender = Calendar.getInstance();
		kalender.setTimeInMillis(System.currentTimeMillis());
		int y = kalender.DAY_OF_YEAR;

		if (berat.length() == 0 && tinggi.length() == 0) {
			pesan = "Masih ada field yang belum diisi";
			return false;
		}

		else if (!(berat.matches("^[0-9]{2,3}(\\.[0-9][0-9]?)?$"))
				|| !(tinggi.matches("^[0-9]{2,3}(\\.[0-9][0-9]?)?$"))) {

			if (!(berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")))
				list.add("Berat Sekarang");
			if (!(tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")))
				list.add("Tinggi Sekarang");

			for (int ii = 0; ii < list.size(); ii++) {
				pesan = pesan + list.get(ii);
				if (ii < list.size() - 2) {
					pesan = pesan + ", ";
				}

				if (ii == list.size() - 2) {
					pesan = pesan + " dan ";
				}
			}
			pesan = pesan + " Salah format";

			return false;
		} else {
			double beratSkrg = Double.parseDouble(berat);
			double tinggiSkrg = Double.parseDouble(tinggi);
			if (beratSkrg == 0 || tinggiSkrg == 0) {
				pesan = "Data yang dimasukkan tidak logis";
				return false;
			} else if (lastBerat != 0 && lastBerat != 0) {
				double difBerat = beratSkrg - lastBerat;
				double difTinggi = tinggiSkrg - lastTinggi;

				selisihHari = y - tglLalu;
				int maksHari = 7;
				if (difTinggi < 0 || Math.abs(difBerat) >= 5.0
						|| Math.abs(difTinggi) >= 2.0) {
					pesan = "Data yang dimasukkan tidak logis";
					return false;

				} else {
					return true;
				}
			} else {
				return true;
			}

		}
	}
}