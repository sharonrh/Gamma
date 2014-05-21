package controller;

import model.Pengguna;
import android.content.Context;
import dao.HandlerProfil;

public class ProfilController {
	private HandlerProfil db;
	public final String[] gayaHidup = { "Pilih Jenis Gaya Hidup",
			"Tidak Aktif", "Kurang Aktif", "Aktif", "Sangat Aktif" };

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

	public int getKebutuhanKal() {
		Pengguna p = getProfil();
		double kal = 0;

		if (p.getGender() == 'P') {
			kal = 66 + 13.7 * p.getBerat() + 5 * p.getTinggi() - 6.8
					* p.getUmur();
		} else {
			kal = 655 + 9.6 * p.getBerat() + 1.8 * p.getTinggi() - 4.7
					* p.getUmur();
		}

		switch (p.getGayaHidup()) {
		case 1:
			kal *= 1.2;
			break;
		case 2:
			kal *= 1.375;
			break;
		case 3:
			kal *= 1.55;
			break;
		case 4:
			kal *= 1.725;
			break;
		default:
			kal *= 1.2;
			break;
		}
		// tambah dengan kebutuhan kg / minggu * 1000
		kal += (p.getTarget() - p.getBerat())
				/ (p.getEndTime() - p.getStartTime()) / 604800L;
		return (int) Math.ceil(kal);
	}
}