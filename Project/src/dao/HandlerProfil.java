package dao;

import model.Pengguna;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerProfil extends DatabaseHandler {
	private static HandlerProfil sInstance;
	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelProfil = "profil";

	// Kolom tabel profil
	private final String id = "id";
	private final String nama = "nama";
	private final String umur = "umur";
	private final String berat = "berat";
	private final String tinggi = "tinggi";
	private final String target = "target";
	private final String gender = "gender";
	private final String gayaHidup = "gayaHidup";
	private final String kacang = "kacang";
	private final String seafood = "seafood";
	private final String hewani = "hewani";
	private final String foto = "foto";
	private final String startTime = "startTime";
	private final String endTime = "endTime";

	public static HandlerProfil getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HandlerProfil(context);
		}
		return sInstance;
	}

	public HandlerProfil(Context context) {
		super(context);
	}

	// Getting single profil
	public Pengguna getProfil() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelProfil, new String[] { id, nama, umur,
				berat, tinggi, target, gender, gayaHidup, kacang, seafood,
				hewani, foto, startTime, endTime }, id + "=?", new String[] { String.valueOf(1) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		int i = Integer.parseInt(cursor.getString(6));
		char g = 'P';
		if (i == 1)
			g = 'W';

		Pengguna profil = new Pengguna(cursor.getString(1),
				Integer.parseInt(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)),
				Double.parseDouble(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)), g,
				Integer.parseInt(cursor.getString(7)), false, false, false,
				cursor.getString(11), Long.parseLong(cursor.getString(12)),
				Long.parseLong(cursor.getString(13)));

		// retrieve data alergi
		int h = Integer.parseInt(cursor.getString(8));
		int s = Integer.parseInt(cursor.getString(9));
		int k = Integer.parseInt(cursor.getString(10));

		if (h == 1) {
			profil.setVegetarian(false);
		}
		if (s == 1) {
			profil.setAlergiSeafood(true);
		}
		if (k == 1) {
			profil.setAlergiKacang(true);
		}

		return profil;
	}

	// Adding new profil
	public boolean updateProfil(String namaNew, int umurNew, double beratNew,
			double tinggiNew, double targetNew, char genderNew,
			int gayaHidupNew, boolean isAlergiKacang, boolean isAlergiSeafood,
			boolean isVegetarian, String fotoNew, long startTime, long endTime) {
		SQLiteDatabase db = this.getWritableDatabase();
		int h = 0, s = 0, k = 0, g = 0;
		ContentValues values = new ContentValues();
		values.put(nama, namaNew);
		values.put(umur, umurNew);
		values.put(berat, beratNew);
		values.put(tinggi, tinggiNew);
		values.put(target, targetNew);
		System.out.println("Gender = " + genderNew);
		// pria = 0, wanita = 1
		if (genderNew == 'W') {
			g = 1;
		}
		values.put(gender, g);
		values.put(gayaHidup, gayaHidupNew);
		if (isAlergiKacang) {
			k = 1;
		}
		values.put(kacang, k);

		if (isAlergiSeafood) {
			s = 1;
		}
		values.put(seafood, s);

		if (isVegetarian) {
			h = 1;
		}
		values.put(hewani, h);
		values.put(foto, fotoNew);
		values.put(this.startTime, startTime);
		values.put(this.endTime, endTime);

		// updating row
		return db.update(tabelProfil, values, KEY_ID + " = ?",
				new String[] { String.valueOf(1) }) > 0;
	}

	// Getting profils Count
	public int getProfilCount() {
		String countQuery = "SELECT  * FROM " + tabelProfil;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

}
