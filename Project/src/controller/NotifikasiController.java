package controller;

import java.util.List;

import model.Laporan;
import model.Notifikasi;
import android.content.Context;
import android.text.format.Time;
import dao.DatabaseHandlerNotifikasi;

public class NotifikasiController {
	private DatabaseHandlerNotifikasi db;

	public NotifikasiController(Context c) {
		db = new DatabaseHandlerNotifikasi(c);
	}

	public boolean addNotifikasi(String nama, long waktu, String pesan) {
		Notifikasi notifObj = new Notifikasi(nama, waktu, pesan);
		return db.tambahNotifikasi(notifObj);
	}

	public List<Notifikasi> getListLaporan() {
		return db.getAllNotifikasi();
	}
}
