package controller;

import model.Pengguna;
import android.content.Context;
import dao.HandlerProfil;

public class ProfilController {
	private HandlerProfil db;

	public ProfilController(Context c) {
		db = HandlerProfil.getInstance(c);
	}

	public boolean updateProfil(String nama, int umur, double berat,
			double tinggi, double target, char gender, int gayaHidup,
			boolean isAlergiKacang, boolean isAlergiSeafood,
			boolean isVegetarian, String foto) {

		return db.updateProfil(nama, umur, berat, tinggi, target, gender,
				gayaHidup, isAlergiKacang, isAlergiSeafood, isVegetarian, foto);
	}

	public Pengguna getProfil() {
		return db.getProfil();
	}

}
