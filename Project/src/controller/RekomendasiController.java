package controller;

import java.util.List;

import model.Makanan;
import android.content.Context;
import dao.HandlerMakanan;

public class RekomendasiController {

	private HandlerMakanan db;
	private List<Makanan> rekomen;

	private final boolean[] count = { true, false, true, true, false, false,
			true, false, false };
	private final String[] header = { "Sarapan", null, "Snack", "Makan siang",
			null, null, "Makan Malam" };

	public RekomendasiController(Context c) {
		db = HandlerMakanan.getInstance(c);

	}

	public List<Makanan> getRekomendasi() {
		rekomen = db.getRekomendasi();
		return rekomen;
	}

	public boolean[] getCount() {
		return count;
	}

	public String[] getHeader() {
		return header;
	}

}
