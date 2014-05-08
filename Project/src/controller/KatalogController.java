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
	
	public int[] getJenisCount() {
		return db.getJenisCount();
	}
}
