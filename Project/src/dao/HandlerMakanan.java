package dao;

import java.util.ArrayList;
import java.util.List;

import model.Makanan;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandlerMakanan extends DatabaseHandler {

	private static final String KEY_ID = "nama";
	private static HandlerMakanan sInstance;

	// Nama tabel yang akan dibuat
	private static final String tabelMakanan = "makanan";

	// Kolom tabel makanan
	private static final String namaMakanan = "nama";
	private static final String kalori = "kalori";
	private static final String protein = "protein";
	private static final String karbo = "karbohidrat";
	private static final String lemak = "lemak";
	private static final String natrium = "natrium";
	private static final String porsi = "porsi";
	private static final String bobot = "bobot";
	private static final String rating = "rating";
	private static final String jenis = "jenis";
	private static final String hewani = "hewani";
	private static final String seafood = "seafood";
	private static final String kacang = "kacang";
	private static final String terakhir = "terakhirDipilih";
	private static final String pathFoto = "pathFoto";

	public static HandlerMakanan getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new HandlerMakanan(context);
		}
		return sInstance;
	}

	private HandlerMakanan(Context context) {
		super(context);
	}

	// Adding new makanan
	public void tambahMakanan(Makanan makanan) {
		SQLiteDatabase db = this.getWritableDatabase();
		int h = 0, s = 0, k = 0;
		ContentValues values = new ContentValues();
		values.put(namaMakanan, makanan.getNama());
		values.put(kalori, makanan.getKalori());
		values.put(protein, makanan.getProtein());
		values.put(karbo, makanan.getKarbohidrat());
		values.put(lemak, makanan.getLemak());
		values.put(natrium, makanan.getNatrium());
		values.put(porsi, makanan.getPorsi());
		values.put(bobot, makanan.getBobot());
		values.put(rating, makanan.getRating());
		values.put(jenis, makanan.getJenisMakanan());
		if (makanan.isHewani()) {
			h = 1;
		}
		if (makanan.isSeafood()) {
			s = 1;
		}
		if (makanan.isKacang()) {
			k = 1;
		}
		values.put(hewani, h);
		values.put(seafood, s);
		values.put(kacang, k);
		values.put(terakhir, makanan.getTerakhirDipilih());
		values.put(pathFoto, makanan.getPathFoto());

		db.insert(tabelMakanan, null, values);
		db.close();
	}

	// Getting single makanan
	public Makanan getMakanan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelMakanan, new String[] { namaMakanan,
				kalori, protein, karbo, lemak, natrium, porsi, bobot, rating,
				jenis, hewani, seafood, kacang, terakhir, pathFoto },
				namaMakanan + "=?", new String[] { String.valueOf(nama) },
				null, null, null, null);

		Makanan makanan = null;
		if (cursor.moveToFirst()) {
			makanan = new Makanan(cursor.getString(0), Integer.parseInt(cursor
					.getString(1)), Double.parseDouble(cursor.getString(2)),
					Double.parseDouble(cursor.getString(3)),
					Double.parseDouble(cursor.getString(4)),
					Double.parseDouble(cursor.getString(5)),
					cursor.getString(6), Integer.parseInt(cursor.getString(7)),
					Integer.parseInt(cursor.getString(8)), cursor.getString(9),
					false, false, false, Long.parseLong(cursor.getString(13)),
					cursor.getString(14));

			// retrieve data alergi
			int h = Integer.parseInt(cursor.getString(10));
			int s = Integer.parseInt(cursor.getString(11));
			int k = Integer.parseInt(cursor.getString(12));

			makanan.setHewani(h == 1);
			makanan.setSeafood(s == 1);
			makanan.setKacang(k == 1);

		}
		cursor.close();
		db.close();
		return makanan;
	}

	// Getting All Makanan
	public List<Makanan> getAllMakanan() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		String selectQuery = "SELECT  * FROM " + tabelMakanan;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(Integer.parseInt(cursor.getString(1)));
				makanan.setProtein(Double.parseDouble(cursor.getString(2)));
				makanan.setKarbohidrat(Double.parseDouble(cursor.getString(3)));
				makanan.setLemak(Double.parseDouble(cursor.getString(4)));
				makanan.setNatrium(Double.parseDouble(cursor.getString(5)));
				makanan.setPorsi(cursor.getString(6));
				makanan.setBobot(Integer.parseInt(cursor.getString(7)));
				makanan.setRating(Integer.parseInt(cursor.getString(8)));
				makanan.setJenisMakanan(cursor.getString(9));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(10));
				int s = Integer.parseInt(cursor.getString(11));
				int k = Integer.parseInt(cursor.getString(12));

				makanan.setHewani(h == 1);
				makanan.setSeafood(s == 1);
				makanan.setKacang(k == 1);
				makanan.setTerakhirDipilih(Long.parseLong(cursor.getString(13)));
				makanan.setPathFoto(cursor.getString(14));

				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return makananList;
	}

	public List<Makanan> getRekomendasi() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		// algo rekomendasi?
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT nama,kalori,porsi,bobot FROM "
				+ tabelMakanan + " ORDER BY RANDOM() LIMIT 9";
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(Integer.parseInt(cursor.getString(1)));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(Integer.parseInt(cursor.getString(3)));
				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
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
	public int[] getJenisCount() {
		String[] arrJenis = { "Makanan Pokok", "Lauk Pauk", "Sayur", "Buah",
				"Minuman" };
		int[] count = new int[arrJenis.length];

		SQLiteDatabase db = this.getReadableDatabase();
		for (int i = 0; i < arrJenis.length; i++) {
			String countQuery = "SELECT  * FROM " + tabelMakanan + " WHERE "
					+ jenis + " = '" + arrJenis[i]+"'";
			Cursor cursor = db.rawQuery(countQuery, null);
			count[i] = cursor.getCount();
			cursor.close();
		}
		db.close();

		return count;
	}
}
