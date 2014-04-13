package controller;

import model.Pengguna;
import android.content.Context;
import dao.DatabaseHandlerProfil;

public class ProfilController {
	private DatabaseHandlerProfil db;

	public ProfilController(Context c) {
		db = DatabaseHandlerProfil.getInstance(c);
	}

	public boolean updateProfil(String nama, int umur, double berat,
			double tinggi, double target, char gender, int gayaHidup,
			boolean isAlergiKacang, boolean isAlergiSeafood,
			boolean isVegetarian) {

		return db.updateProfil(nama, umur, berat, tinggi, target, gender,
				gayaHidup, isAlergiKacang, isAlergiSeafood, isVegetarian);
	}

	public Pengguna getProfil() {
		return db.getProfil();
	}

}
