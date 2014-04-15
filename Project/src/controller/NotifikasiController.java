package controller;

import java.util.List;

import model.Notifikasi;
import android.content.Context;
import dao.HandlerNotifikasi;

public class NotifikasiController {
	private HandlerNotifikasi db;

	public NotifikasiController(Context c) {
		db = new HandlerNotifikasi(c);
	}

	public boolean addNotifikasi(String nama, long waktu, String pesan) {
		Notifikasi notifObj = new Notifikasi(nama, waktu, pesan);
		return db.tambahNotifikasi(notifObj);
	}

	public List<Notifikasi> getListNotifikasi() {
		return db.getAllNotifikasi();
	}
}
