package controller;

import java.util.List;

import model.Makanan;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import dao.HandlerMakanan;
import dao.HandlerRekomendasi;

public class RekomendasiController {

	private HandlerRekomendasi dbRekomen;
	private HandlerMakanan dbMakanan;
	private List<Makanan> rekomen;
	private SharedPreferences prefs;

	private final boolean[] count = { true, false, true, true, false, false,
			true, true, false, false };
	private final String[] header = { "Sarapan", null, "Snack", "Makan siang",
			null, null, "Snack", "Makan Malam" };

	public RekomendasiController(Context c) {
		dbRekomen = HandlerRekomendasi.getInstance(c);
		dbMakanan = HandlerMakanan.getInstance(c);

		prefs = c.getSharedPreferences("com.example.gamma", c.MODE_PRIVATE);
	}

	public List<Makanan> getRekomendasi(double kal) {
		Time now = new Time();
		now.setToNow();

		Time rekTime = new Time();
		rekTime.set(prefs.getLong("rekTime", 0));

		// jika beda tanggal
		if (rekTime.year != now.year || rekTime.yearDay != now.yearDay) {
			rekomen = dbMakanan.getRekomendasi(kal);
			dbRekomen.setRekomendasi(rekomen);
			rekTime.setToNow();
			prefs.edit().putLong("rekTime", rekTime.toMillis(true)).commit();
		} else {
			rekomen = dbRekomen.getRekomendasi();
		}

		return rekomen;
	}

	public boolean[] getCount() {
		return count;
	}

	public String[] getHeader() {
		return header;
	}

}
