package view;

import model.Pengguna;
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

import controller.ProfilController;

public class ProfilFragment extends Fragment {

	private Button editProfil;
	private TextView nama, umur, beratSekarang, beratTarget, tinggi, telurTxt,
			ikanTxt, kacangTxt, sayurTxt, gayaTxt, akv1Txt, akv2Txt, akv3Txt;
	private ImageView foto, genderImg, telurImg, ikanImg, kacangImg, sayurImg,
			gaya1Img, gaya2Img, gaya3Img, gaya4Img;
	private ProfilController con;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_lihat_profil, container,
				false);

		con = new ProfilController(getActivity().getApplicationContext());
		Pengguna profil = con.getProfil();
		
		editProfil = (Button) v.findViewById(R.id.editProfilButton);
		nama = (TextView) v.findViewById(R.id.namaProfilTv);
		nama.setText(profil.getNama());
		
		
		umur = (TextView) v.findViewById(R.id.tahunProfilTv);
		beratSekarang = (TextView) v.findViewById(R.id.beratProfilSekarangTv);
		beratTarget = (TextView) v.findViewById(R.id.beratProfilTargetTv);
		tinggi = (TextView) v.findViewById(R.id.tinggiProfilTv);

		foto = (ImageView) v.findViewById(R.id.fotoProfilTv);

		genderImg = (ImageView) v.findViewById(R.id.imageGender);

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

		editProfil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View args0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),
						EditProfilActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(i);
			}
		});

		return v;
	}
}