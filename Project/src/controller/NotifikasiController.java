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
		Notifikasi notifObj = new Notifikasi(nama, waktu, pesan, false);
		return db.tambahNotifikasi(notifObj);
	}

	public List<Notifikasi> getListNotifikasi() {
		return db.getAllNotifikasi();
	}
	
	public boolean updateNotifikasi(String namaNotif, long time, boolean selected){
		return db.updateNotif(namaNotif, time, selected);
	}
	public boolean updateNotifikasi(String namaNotif, long time){
		return db.updateNotif(namaNotif, time);
	}
	public boolean updateNotifikasi(String namaNotif, boolean selected){
		return db.updateNotif(namaNotif, selected);
	}
}
