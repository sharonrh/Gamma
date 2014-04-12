package view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.gamma.R;

public class ProfilFragment extends Fragment {
	
	Button editProfil;
	TextView nama;
	TextView umur;
	TextView berat;
	TextView tinggi;
	ImageView foto;
	RadioGroup jekel;
	CheckBox vegetarian;
	CheckBox alergiTelur;
	CheckBox alergiMakananLaut;
	CheckBox alergiKacangKacangan;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_lihat_profil, container, false);
        
        editProfil = (Button) v.findViewById(R.id.editProfilButton);
        nama = (TextView) v.findViewById(R.id.namaProfilTv);
        umur = (TextView) v.findViewById(R.id.tahunProfilTv);
        berat = (TextView) v.findViewById(R.id.beratProfilTv);
        tinggi = (TextView) v.findViewById(R.id.tinggiProfilTv);
        
        foto = (ImageView) v.findViewById(R.id.imageView1);
        
        jekel = (RadioGroup) v.findViewById(R.id.jenisKelamin);
        
        vegetarian = (CheckBox) v.findViewById(R.id.checkBox1);
        alergiTelur = (CheckBox) v.findViewById(R.id.checkBox2);
        alergiMakananLaut = (CheckBox) v.findViewById(R.id.checkBox3);
        alergiKacangKacangan = (CheckBox) v.findViewById(R.id.checkBox4);
        
        editProfil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(), EditProfilActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(i);
			}
		});
       
        return v;
    }    
}