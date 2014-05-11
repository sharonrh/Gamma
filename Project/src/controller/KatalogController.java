package controller;

import java.util.List;

import model.Makanan;
import android.content.Context;
import dao.HandlerMakanan;

public class KatalogController {

	private HandlerMakanan db;

	public KatalogController(Context c) {
		db = HandlerMakanan.getInstance(c);
	}

	public List<Makanan> getListMakanan() {
		return db.getAllMakanan();
	}

	public Makanan getMakanan(String nama) {
		return db.getMakanan(nama);
	}

	public int[] getJenisCount() {
		return db.getJenisCount();
	}
	
	public boolean updateRating(String nama, float rating) {
		return db.updateRating(nama, rating);		
	}
}
