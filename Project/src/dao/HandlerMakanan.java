package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.Makanan;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerMakanan extends DatabaseHandler {

	private static final String KEY_ID = "nama";
	private static HandlerMakanan sInstance;

	// Database Name
	private static final String namaDB = "gamma.db";

	// Nama tabel yang akan dibuat
	private static final String tabelMakanan = "makanan";

	// Kolom tabel makanan
	private static final String namaMakanan = "nama";
	private static final String jlhKalori = "kalori";
	private static final String jlhProtein = "protein";
	private static final String jlhLemak = "lemak";
	private static final String jlhKarbo = "karbohidrat";
	private static final String jlhKalsium = "kalsium";
	private static final String persentase = "persentase";
	private static final String rating = "rating";
	private static final String jenis = "jenis";
	private static final String hewani = "hewani";
	private static final String seafood = "seafood";
	private static final String kacang = "kacang";
	private static final String terakhir = "terakhirDipilih";

	public static HandlerMakanan getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HandlerMakanan(context);
		}
		return sInstance;
	}

	private HandlerMakanan(Context context) {
		super(context);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations untuk tabel Makanan
	 */

	// Adding new makanan
	void tambahMakanan(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();
		int h = 0, s = 0, k = 0;
		ContentValues values = new ContentValues();
		values.put(namaMakanan, makanan.getNama());
		values.put(jlhKalori, makanan.getKalori());
		values.put(jlhProtein, makanan.getProtein());
		values.put(jlhLemak, makanan.getLemak());
		values.put(jlhKarbo, makanan.getKarbohidrat());
		values.put(jlhKalsium, makanan.getKalsium());
		values.put(rating, makanan.getRating());
		values.put(persentase, makanan.getPersentase());
		values.put(jenis, makanan.getJenisMakanan());
		if (makanan.isHewani()) {
			h = 1;
		}
		values.put(hewani, h);

		if (makanan.isSeafood()) {
			s = 1;
		}
		values.put(seafood, s);

		if (makanan.isKacang()) {
			k = 1;
		}
		values.put(kacang, k);
		values.put(terakhir, makanan.getTerakhir());

		// Inserting Row
		db.insert(tabelMakanan, null, values);
	}

	// Getting single makanan
	public Makanan getMakanan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelMakanan, new String[] { namaMakanan,
				"berat", jlhKalori, jlhProtein, jlhLemak, jlhKarbo, jlhKalsium,
				rating, persentase, jenis, hewani, seafood, kacang, terakhir },
				namaMakanan + "=?", new String[] { String.valueOf(nama) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Makanan makanan = new Makanan(cursor.getString(0),
				Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor
						.getString(2)),
				Double.parseDouble(cursor.getString(3)),
				Double.parseDouble(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)),
				Double.parseDouble(cursor.getString(6)),
				Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor
						.getString(8)), cursor.getString(9),
				Long.parseLong(cursor.getString(13)));

		// retrieve data alegi
		int h = Integer.parseInt(cursor.getString(10));
		int s = Integer.parseInt(cursor.getString(11));
		int k = Integer.parseInt(cursor.getString(12));

		if (h == 1) {
			makanan.setHewani(true);
		}
		if (s == 1) {
			makanan.setSeafood(true);
		}
		if (k == 1) {
			makanan.setKacang(true);
		}

		return makanan;
	}

	// Getting All Makanan
	public List<Makanan> getAllMakanan() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelMakanan;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setBerat(Integer.parseInt(cursor.getString(1)));
				makanan.setKalori(Integer.parseInt(cursor.getString(2)));
				makanan.setProtein(Double.parseDouble(cursor.getString(3)));
				makanan.setKarbohidrat(Double.parseDouble(cursor.getString(4)));
				makanan.setLemak(Double.parseDouble(cursor.getString(5)));
				makanan.setKalsium(Double.parseDouble(cursor.getString(6)));
				makanan.setPersentase(Integer.parseInt(cursor.getString(7)));
				makanan.setRating(Integer.parseInt(cursor.getString(8)));
				makanan.setJenisMakanan(cursor.getString(9));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(10));
				int s = Integer.parseInt(cursor.getString(11));
				int k = Integer.parseInt(cursor.getString(12));

				if (h == 1) {
					makanan.setHewani(true);
				}
				if (s == 1) {
					makanan.setSeafood(true);
				}
				if (k == 1) {
					makanan.setKacang(true);
				}
				makanan.setTerakhir(Long.parseLong(cursor.getString(13)));
				// Adding makanan to list
				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		return makananList;
	}

	public List<Makanan> getRekomendasi() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		// Select All Query
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT nama,berat,kalori FROM " + tabelMakanan
				+ " ORDER BY RANDOM() LIMIT 9";
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setBerat(Integer.parseInt(cursor.getString(1)));
				makanan.setKalori(Integer.parseInt(cursor.getString(2)));
				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		return makananList;
	}

	// Deleting single makanan
	public void deleteMakanan(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelMakanan, KEY_ID + " = ?",
				new String[] { String.valueOf(makanan.getNama()) });
		db.close();
	}

	// Getting makanan Count
	public int getMakananCount() {
		String countQuery = "SELECT  * FROM " + tabelMakanan;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}
