package view;

import model.Pengguna;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
	private TextView nama, umur, beratSekarang, beratTarget, tinggi, ikanTxt,
			kacangTxt, sayurTxt, gayaTxt, mulaiTxt, akhirTxt;
	private ImageView foto, genderImg, ikanImg, kacangImg, sayurImg, gaya1Img,
			gaya2Img, gaya3Img, gaya4Img, mulaiImg, akhirImg;
	private ProfilController con;

	Pengguna profil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_lihat_profil, container,
				false);

		con = new ProfilController(getActivity().getApplicationContext());
		profil = con.getProfil();

		editProfil = (Button) v.findViewById(R.id.editProfilButton);
		nama = (TextView) v.findViewById(R.id.namaProfilTv);
		umur = (TextView) v.findViewById(R.id.tahunProfilTv);
		beratSekarang = (TextView) v.findViewById(R.id.beratProfilSekarangTv);
		beratTarget = (TextView) v.findViewById(R.id.beratProfilTargetTv);
		tinggi = (TextView) v.findViewById(R.id.tinggiProfilTv);
		foto = (ImageView) v.findViewById(R.id.fotoProfilTv);

		genderImg = (ImageView) v.findViewById(R.id.imageGender);

		mulaiImg = (ImageView) v.findViewById(R.id.imageMulai);
		mulaiTxt = (TextView) v.findViewById(R.id.textMulai);
		akhirImg = (ImageView) v.findViewById(R.id.imageSelesai);
		akhirTxt = (TextView) v.findViewById(R.id.textSelesai);

		ikanImg = (ImageView) v.findViewById(R.id.imageIkan);
		kacangImg = (ImageView) v.findViewById(R.id.imageKacang);
		sayurImg = (ImageView) v.findViewById(R.id.imageVegetarian);

		gaya1Img = (ImageView) v.findViewById(R.id.imageGayaHidup1);

		nama.setText(profil.getNama());
		umur.setText(profil.getUmur() + " tahun");
		beratSekarang.setText(profil.getBerat() + " kg");
		beratTarget.setText(profil.getTarget() + " kg");
		tinggi.setText(profil.getTinggi() + " cm");

		byte[] decodedString = Base64.decode(profil.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);

		foto.setImageBitmap(decodedByte);

		if (profil.getGender() == 'P')
			genderImg.setBackgroundResource(R.drawable.man);
		else
			genderImg.setBackgroundResource(R.drawable.woman);

		mulaiImg.setBackgroundResource(R.drawable.start);
		akhirImg.setBackgroundResource(R.drawable.finish);
		mulaiTxt.setText(profil.getStartTime() + "");
		akhirTxt.setText(profil.getEndTime() + "");

		if (!profil.isAlergiKacang()) {
			kacangImg.setVisibility(View.GONE);
		} else {
			kacangImg.setBackgroundResource(R.drawable.alergi_kacang);
		}

		if (!profil.isAlergiSeafood()) {
			ikanImg.setVisibility(View.GONE);
		} else
			ikanImg.setBackgroundResource(R.drawable.alergi_seafood);

		if (!profil.isVegetarian()) {
			sayurImg.setImageResource(R.drawable.non_veg);
		} else {
			sayurImg.setImageResource(R.drawable.vegetarian);
		}

		if (profil.getGayaHidup() == 3) {
			gaya1Img.setBackgroundResource(R.drawable.sangat_aktif);
		}

		else if (profil.getGayaHidup() == 2) {
			gaya1Img.setBackgroundResource(R.drawable.aktif);
		}

		else if (profil.getGayaHidup() == 1) {
			gaya1Img.setBackgroundResource(R.drawable.sedikit_aktif);
		}

		else {
			gaya1Img.setBackgroundResource(R.drawable.jarang_sekali);
		}

		editProfil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View args0) {
				// TODO Auto-generated method stub
				getActivity().finish();
				Intent i = new Intent(getActivity().getApplicationContext(),
						EditProfilActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				i.putExtra("nama", nama.getText());
				i.putExtra("umur", umur.getText());
				i.putExtra("beratSkrg", beratSekarang.getText());
				i.putExtra("beratTarget", beratTarget.getText());
				i.putExtra("tinggi", tinggi.getText());
				i.putExtra("jeKel", profil.getGender());
				i.putExtra("foto", profil.getFoto());
				i.putExtra("sayur", profil.isVegetarian());
				i.putExtra("gayaHidup", profil.getGayaHidup());
				i.putExtra("ikan", profil.isAlergiSeafood());
				i.putExtra("kacang", profil.isAlergiKacang());

				getActivity().startActivity(i);
			}
		});

		return v;
	}
}