package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Laporan;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

import controller.LaporanController;

public class LaporanFragment extends Fragment {

	private EditText beratField;
	private EditText tinggiField;
	private TextView beratLalu;
	private TextView tinggiLalu;
	TextView tglHariLalu;
	TextView tglHariIni;
	private Button simpanBtn;
	private Button batalBtn;
	private boolean cek = false;
	private String pesan = "";
	double lastBerat=0;
	double lastTinggi=0;
	int tglLalu=1;
	int selisihHari=0;
	private LaporanController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_laporan, container, false);

		kontrol = new LaporanController(getActivity().getApplicationContext());
		beratField = (EditText) v.findViewById(R.id.beratIsiLaporan);
		tinggiField = (EditText) v.findViewById(R.id.tinggiIsiLaporan);
		tglHariLalu = (TextView) v.findViewById(R.id.tanggalHariSebelumnya);
		tglHariIni = (TextView) v.findViewById(R.id.tanggalHariIni);
		simpanBtn = (Button) v.findViewById(R.id.button1);
		batalBtn = (Button) v.findViewById(R.id.button2);
		beratLalu = (TextView) v.findViewById(R.id.beratLaluIsiLaporan);
		tinggiLalu = (TextView) v.findViewById(R.id.tinggiLaluIsiLaporan);

		List<Laporan> data = kontrol.getListLaporan();
		
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");	
		String formatted = format.format(date);
		
		tglHariLalu.setText("Data Sebelumnya (" + selisihHari + " hari yang lalu)");
		tglHariIni.setText("Data untuk tanggal " + formatted);
		
		if(!data.isEmpty()){
			
			Laporan lastLaporan = data.get(data.size()-1);
			
			lastBerat = lastLaporan.getBeratBadan();
			lastTinggi = lastLaporan.getTinggiBadan();
			
			Calendar cal = Calendar.getInstance();
			
			long waktuLalu = lastLaporan.getWaktu();
			
			cal.setTimeInMillis(waktuLalu);
			tglLalu = cal.DAY_OF_YEAR;
			
			beratLalu.setText(lastBerat + " kg");
			tinggiLalu.setText(lastTinggi + " cm");
			
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
					Toast.makeText(getActivity().getApplicationContext(),
							pesan + ".", Toast.LENGTH_LONG).show();
					pesan = "";
				}

				if (cek) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Laporan berhasil disimpan", Toast.LENGTH_LONG)
							.show();
					beratLalu.setText(berat + " kg");
					tinggiLalu.setText(tinggi + " cm");

				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"Laporan gagal disimpan", Toast.LENGTH_LONG).show();
				}
			}
		});

		batalBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		return v;

	}

public boolean validasiInput(String berat, String tinggi){
		
		double difBerat = Double.parseDouble(berat) - lastBerat;
		double difTinggi = Double.parseDouble(tinggi) - lastTinggi;
		
		String pesan1 = "";
		ArrayList<String> list = new ArrayList<String>();
		
		Calendar kalender = Calendar.getInstance();
		kalender.setTimeInMillis(System.currentTimeMillis());
		int y = kalender.DAY_OF_YEAR;
		
		if(berat.length()!=0 && tinggi.length()!=0 ){
			if(!(berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))) 
				list.add("Berat Sekarang");
			if(!(tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")))
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
		} 
		else {
			pesan = "Masih ada field yang belum Kamu isi";
		}
		
		if(Math.abs(difBerat) >= 5.0 || Math.abs(difTinggi) >= 2.0){
			pesan = "Data yang kamu masukkan tidak logis";
			
			return false;
		}
		
		selisihHari = y-tglLalu;
		int maksHari = 7;
		if(selisihHari < 7){
			pesan = "Kamu baru bisa mengisi kembali " + (maksHari - selisihHari) + " hari lagi";
			
			return false;
		}
		
		
		return berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")&&
				tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$");
	}
}