package dao;

import java.util.ArrayList;
import java.util.List;

import model.Makanan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerRekomendasi extends DatabaseHandler {

	private static final String KEY_ID = "nama";
	private static HandlerRekomendasi sInstance;

	// Nama tabel yang akan dibuat
	private static final String tabelRekomendasi = "rekomendasi";

	private static final String namaMakanan = "nama";
	private static final String kalori = "kalori";
	private static final String porsi = "porsi";
	private static final String bobot = "bobot";
	
	public static HandlerRekomendasi getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HandlerRekomendasi(context);
		}
		return sInstance;
	}

	private HandlerRekomendasi(Context context) {
		super(context);
	}

	// Getting All Makanan
	public List<Makanan> getRekomendasi() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		String selectQuery = "SELECT  * FROM " + tabelRekomendasi;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));

				makananList.add(makanan);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return makananList;
	}
	
	public void setRekomendasi(List<Makanan> makanan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values;
		
		for (Makanan m : makanan) {
			values = new ContentValues();
			values.put(namaMakanan, m.getNama());
			values.put(kalori, m.getKalori());
			values.put(porsi, m.getPorsi());
			values.put(bobot, m.getBobot());
			db.insert(tabelRekomendasi, null, values);
		}
		db.close();
	}
}
