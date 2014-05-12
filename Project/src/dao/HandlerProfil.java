package dao;

import model.Pengguna;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerProfil extends DatabaseHandler {
	private static HandlerProfil sInstance;
	private static final String KEY_ID = "id";

	private static final String tabelProfil = "profil";

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
	private final String vegetarian = "vegetarian";
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

	public Pengguna getProfil() {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelProfil, new String[] { id, nama, umur,
				berat, tinggi, target, gender, gayaHidup, kacang, seafood,
				vegetarian, foto, startTime, endTime }, id + "=?",
				new String[] { String.valueOf(1) }, null, null, null, null);

		Pengguna profil = null;
		if (cursor.moveToFirst()) {

			int i = Integer.parseInt(cursor.getString(6));
			char g = i == 1 ? 'W' : 'P';

			profil = new Pengguna(cursor.getString(1), cursor.getInt(2),
					cursor.getDouble(3), cursor.getDouble(4),
					cursor.getDouble(5), g, cursor.getInt(7), false, false,
					false, cursor.getString(11), cursor.getLong(12),
					cursor.getLong(13));

			// retrieve data alergi
			int h = Integer.parseInt(cursor.getString(8));
			int s = Integer.parseInt(cursor.getString(9));
			int k = Integer.parseInt(cursor.getString(10));

			profil.setVegetarian(h == 1);
			profil.setAlergiSeafood(s == 1);
			profil.setAlergiKacang(k == 1);
		}
		cursor.close();
		db.close();
		return profil;
	}

	public boolean updateProfil(String namaNew, int umurNew, double beratNew,
			double tinggiNew, double targetNew, char genderNew,
			int gayaHidupNew, boolean isAlergiKacang, boolean isAlergiSeafood,
			boolean isVegetarian, String fotoNew, long startNew, long endNew) {
		SQLiteDatabase db = this.getWritableDatabase();
		int h = 0, s = 0, k = 0, g = 0;
		ContentValues values = new ContentValues();
		values.put(nama, namaNew);
		values.put(umur, umurNew);
		values.put(berat, beratNew);
		values.put(tinggi, tinggiNew);
		values.put(target, targetNew);
		// pria = 0, wanita = 1
		if (genderNew == 'W') {
			g = 1;
		}
		values.put(gender, g);
		values.put(gayaHidup, gayaHidupNew);
		if (isAlergiKacang) {
			k = 1;
		}
		if (isAlergiSeafood) {
			s = 1;
		}
		if (isVegetarian) {
			h = 1;
		}
		values.put(kacang, k);
		values.put(seafood, s);
		values.put(vegetarian, h);
		values.put(foto, fotoNew);
		values.put(startTime, startNew);
		values.put(endTime, endNew);
	
		boolean b = db.update(tabelProfil, values, KEY_ID + " = ?",
				new String[] { String.valueOf(1) }) > 0;
		db.close();

		return b;
	}
}
