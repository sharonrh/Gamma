package view;

import java.util.ArrayList;

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

	private Button simpanBtn;
	private Button batalBtn;
	private boolean cek = false;
	private String pesan = "";
	private LaporanController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_laporan, container, false);

		kontrol = new LaporanController(getActivity().getApplicationContext());
		beratField = (EditText) v.findViewById(R.id.beratIsiLaporan);
		tinggiField = (EditText) v.findViewById(R.id.tinggiIsiLaporan);
		simpanBtn = (Button) v.findViewById(R.id.button1);
		batalBtn = (Button) v.findViewById(R.id.button2);
		beratLalu = (TextView) v.findViewById(R.id.beratLaluIsiLaporan);
		tinggiLalu = (TextView) v.findViewById(R.id.tinggiLaluIsiLaporan);

		Laporan laporanTerbaru = kontrol.getLaporanTerbaru();

		if (laporanTerbaru != null) {
			beratLalu.setText(laporanTerbaru.getBeratBadan() + " kg");
			tinggiLalu.setText(laporanTerbaru.getTinggiBadan() + " cm");
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

	public boolean validasiInput(String berat, String tinggi) {

		ArrayList<String> list = new ArrayList<String>();

		if (berat.length() != 0 && tinggi.length() != 0) {
			if (!berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
				list.add("Berat Sekarang");
			if (!tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
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
		} else {
			pesan = "Masih ada field yang belum diisi";
		}

		return berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")
				&& tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$");
	}
}