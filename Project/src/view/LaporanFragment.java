package view;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamma.R;

import controller.LaporanController;

public class LaporanFragment extends Fragment {

	EditText beratField;
	EditText tinggiField;
	Button simpanBtn;
	Button batalBtn;

	boolean cek = false;
	LaporanController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_laporan, container, false);
		kontrol = new LaporanController(getActivity().getApplicationContext());
		beratField = (EditText) v.findViewById(R.id.berat);
		tinggiField = (EditText) v.findViewById(R.id.tinggi);
		simpanBtn = (Button) v.findViewById(R.id.button1);

		simpanBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String berat = beratField.getText().toString();
				String tinggi = tinggiField.getText().toString();

				cek = false;

				if (berat.length() != 0 && tinggi.length() != 0) {
					cek = kontrol.addLaporan(berat, tinggi);
				}

				if (cek) {
					System.out.println(kontrol.getListLaporan().toString());

					AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

					// Setting Dialog Title
					alertDialog.setTitle("Sukses");

					// Setting Dialog Message
					alertDialog.setMessage("Laporan berhasil disimpan :) ");

					// Setting Icon to Dialog
					alertDialog.setIcon(R.drawable.ic_launcher);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					// Showing Alert Message
					alertDialog.show();
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

					// Setting Dialog Title
					alertDialog.setTitle("Gagal");

					// Setting Dialog Message
					alertDialog.setMessage("Laporan gagal disimpan :(");

					// Setting Icon to Dialog
					alertDialog.setIcon(R.drawable.ic_launcher);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					// Showing Alert Message
					alertDialog.show();
				}

				// tv.setText(kontrol.getListLaporan().toString());
			}
		});

		batalBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return v;

	}
}