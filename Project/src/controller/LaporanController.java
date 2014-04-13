package controller;

import java.util.List;

import model.Laporan;
import android.content.Context;
import android.text.format.Time;
import dao.DatabaseHandlerLaporan;

public class LaporanController {

	private DatabaseHandlerLaporan db;

	public LaporanController(Context c) {
		db = DatabaseHandlerLaporan.getInstance(c);
	}

	public boolean addLaporan(String berat, String tinggi) {
		Time t = new Time();
		t.setToNow();
		long l = t.toMillis(false);
		return db.tambahLaporan(l, berat, tinggi);
	}

	public List<Laporan> getListLaporan() {
		return db.getAllLaporan();
	}
}
