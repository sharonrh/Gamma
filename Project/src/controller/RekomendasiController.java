package controller;

import java.util.List;

import model.Makanan;

public class RekomendasiController {

	private List<Makanan> listMakanan;
	private boolean[] count = { true, false, true, true, false, false, true,
			false, false };
	private String[] header = { "Sarapan", null, "Snack", "Makan siang", null,
			null, "Makan Malam" };

	public List<Makanan> getListMakanan() {
		return listMakanan;
	}

	public boolean[] getCount() {
		return count;
	}

	public String[] getHeader() {
		return header;
	}

}
