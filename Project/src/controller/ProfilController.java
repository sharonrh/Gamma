package controller;

import model.Pengguna;
import android.content.Context;
import dao.HandlerProfil;

public class ProfilController {
	private HandlerProfil db;
	public final String[] gayaHidup = { "Pilih Jenis Gaya Hidup",
			"Jarang Sekali", "Sedikit Aktif", "Aktif", "Sangat Aktif" };

	public ProfilController(Context c) {
		db = HandlerProfil.getInstance(c);
	}

	public boolean updateProfil(String nama, int umur, double berat,
			double tinggi, double target, char gender, int gayaHidup,
			boolean isAlergiKacang, boolean isAlergiSeafood,
			boolean isVegetarian, String foto, long start, long end) {

		return db.updateProfil(nama, umur, berat, tinggi, target, gender,
				gayaHidup, isAlergiKacang, isAlergiSeafood, isVegetarian, foto,
				start, end);
	}

	public Pengguna getProfil() {
		return db.getProfil();
	}
}