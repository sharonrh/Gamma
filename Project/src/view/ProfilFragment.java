package view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamma.R;

public class ProfilFragment extends Fragment {
	
	Button editProfil;
	TextView nama, umur, beratSekarang, beratTarget, tinggi, 
		     telurTxt, ikanTxt, kacangTxt, sayurTxt, gayaTxt, 
		     akv1Txt, akv2Txt, akv3Txt;
	ImageView foto, genderImg, telurImg, ikanImg, kacangImg, 
			  sayurImg, gaya1Img, gaya2Img, gaya3Img, gaya4Img, 
			  akv1Img, akv2Img, akv3Img;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_lihat_profil, container, false);
        
        editProfil = (Button) v.findViewById(R.id.editProfilButton);
        nama = (TextView) v.findViewById(R.id.namaProfilTv);
        umur = (TextView) v.findViewById(R.id.tahunProfilTv);
        beratSekarang = (TextView) v.findViewById(R.id.beratProfilSekarangTv);
        beratTarget = (TextView) v.findViewById(R.id.beratProfilTargetTv);
        tinggi = (TextView) v.findViewById(R.id.tinggiProfilTv);
        
        foto = (ImageView) v.findViewById(R.id.fotoProfilTv);
        
        genderImg = (ImageView) v.findViewById(R.id.imageGender);
        
        //telurImg = (ImageView) v.findViewById(R.id.imageTelur);
        //telurTxt = (TextView) v.findViewById(R.id.textTelur);
        ikanImg = (ImageView) v.findViewById(R.id.imageIkan);
        ikanTxt = (TextView) v.findViewById(R.id.textIkan);
        kacangImg = (ImageView) v.findViewById(R.id.imageKacang);
        kacangTxt = (TextView) v.findViewById(R.id.textKacang);
        
        sayurImg = (ImageView) v.findViewById(R.id.imageVegetarian);
        sayurTxt = (TextView) v.findViewById(R.id.textVegetarian);
        
        gaya1Img = (ImageView) v.findViewById(R.id.imageGayaHidup1);
        gaya2Img = (ImageView) v.findViewById(R.id.imageGayaHidup2);
        gayaTxt = (TextView) v.findViewById(R.id.textGayaHidup);
        gaya3Img = (ImageView) v.findViewById(R.id.imageGayaHidup3);
        gaya4Img = (ImageView) v.findViewById(R.id.imageGayaHidup4);
        
        //akv1Img = (ImageView) v.findViewById(R.id.imageAktivitas1);
        //akv1Txt = (TextView) v.findViewById(R.id.textAktivitas1);
        //akv2Img = (ImageView) v.findViewById(R.id.imageAktivitas2);
        //akv2Txt = (TextView) v.findViewById(R.id.textAktivitas2);
        //akv3Img = (ImageView) v.findViewById(R.id.imageAktivitas3);
        //akv3Txt = (TextView) v.findViewById(R.id.textAktivitas3);
        
                
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