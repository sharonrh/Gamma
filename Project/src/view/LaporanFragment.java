package view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gamma.R;

import controller.LaporanController;

public class LaporanFragment extends Fragment {

	EditText beratField;
	EditText tinggiField;
	Button simpanBtn;
	Button batalBtn;
	boolean cek = false;
	String pesan = "";
	LaporanController kontrol;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_laporan, container, false);

		kontrol = new LaporanController(getActivity().getApplicationContext());
		//beratField = (EditText) v.findViewById(R.id.berat);
		//tinggiField = (EditText) v.findViewById(R.id.tinggi);
		simpanBtn = (Button) v.findViewById(R.id.button1);
		batalBtn = (Button) v.findViewById(R.id.button2);

		simpanBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String berat = beratField.getText().toString();
				String tinggi = tinggiField.getText().toString();

				cek = false;

				if (validasiInput(berat, tinggi)) {
					cek = kontrol.addLaporan(berat, tinggi);
				}
				else {
					Toast.makeText(getActivity().getApplicationContext(),
							pesan + ".",
							Toast.LENGTH_LONG).show();
					
					pesan = "";
				}

				if (cek) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Laporan Kamu berhasil disimpan",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"Laporan Kamu gagal disimpan",
							Toast.LENGTH_LONG).show();
				}

				// tv.setText(kontrol.getListLaporan().toString());
			}
		});

		batalBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

		return v;

	}
	
	public boolean validasiInput(String berat, String tinggi){
		
		String pesan1 = "";
		ArrayList<String> list = new ArrayList<String>();
		
		
		if(berat.length()!=0 && tinggi.length()!=0 ){
			if(!berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
				list.add("Berat Sekarang");
			if(!tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
				list.add("Tinggi Sekarang");
			
			for(int ii = 0; ii< list.size(); ii++){
				pesan = pesan + list.get(ii);
				if(ii < list.size() - 2){
					pesan = pesan + ", ";
				}
				
				if(ii == list.size()-2){
					pesan = pesan + " dan ";
				}
				
			}
			
			pesan = pesan + " Kamu, salah format";
		}
		else {
			pesan = "Masih ada field yang belum Kamu isi";
		}
		
		
		return berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")&&
				tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$");
	}
}