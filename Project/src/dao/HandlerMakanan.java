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
	private static final String waktu = "waktuBaik";
	private static final String kombinasi = "kombinasi";

	private final String[] arrJenis = { "'Pokok'", "'Lauk'", "'Sayuran'", "'Buah'",
			"'Minuman'", "'Snack'" };

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
			makanan = new Makanan(cursor.getString(0), cursor.getInt(1),
					cursor.getDouble(2), cursor.getDouble(3),
					cursor.getDouble(4), cursor.getDouble(5),
					cursor.getString(6), cursor.getInt(7), cursor.getFloat(8),
					cursor.getString(9), false, false, false,
					cursor.getLong(13), cursor.getString(14));

			// retrieve data alergi
			int h = cursor.getInt(10);
			int s = cursor.getInt(11);
			int k = cursor.getInt(12);

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
				makanan.setKalori(cursor.getInt(1));
				makanan.setProtein(cursor.getDouble(2));
				makanan.setKarbohidrat(cursor.getDouble(3));
				makanan.setLemak(cursor.getDouble(4));
				makanan.setNatrium(cursor.getDouble(5));
				makanan.setPorsi(cursor.getString(6));
				makanan.setBobot(cursor.getInt(7));
				makanan.setRating(cursor.getFloat(8));
				makanan.setJenisMakanan(cursor.getString(9));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(10));
				int s = Integer.parseInt(cursor.getString(11));
				int k = Integer.parseInt(cursor.getString(12));

				makanan.setHewani(h == 1);
				makanan.setSeafood(s == 1);
				makanan.setKacang(k == 1);
				makanan.setTerakhirDipilih(cursor.getLong(13));
				makanan.setPathFoto(cursor.getString(14));

				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return makananList;
	}

	public List<Makanan> getRekomendasi(double kal, boolean v, boolean s,
			boolean k) {
		List<Makanan> makananList = new ArrayList<Makanan>();
		SQLiteDatabase db = this.getWritableDatabase();

		// ----------------- sarapan --------------------
		String cols = String.format("%s,%s,%s,%s,%s,%s", namaMakanan, kalori,
				porsi, bobot, jenis, terakhir);
		String alergi = "";

		if (v)
			alergi += " AND " + hewani + "='0'";
		if (s)
			alergi += " AND " + seafood + "='0'";
		if (k)
			alergi += " AND " + kacang + "='0'";

		String query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan
				+ " WHERE " + waktu + " ='1' AND " + kalori + " >0" + alergi
				+ " ORDER BY random() LIMIT 2";

		System.out.println(query);
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("sarapan : " + makanan.getNama());
			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- snack --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " =" + arrJenis[5] + " AND " + kalori + " >0"
				+ alergi + " ORDER BY random() LIMIT 1";

		cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("snack pagi : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- makan siang (makanan pokok) --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " =" + arrJenis[0] + " AND " + kombinasi + "='2'"
				+ " AND " + kalori + " >0" + alergi
				+ " ORDER BY random() LIMIT 1";

		cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("makan siang : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- makan siang (lauk + sayur) --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " IN (" + arrJenis[1] + "," + arrJenis[2] + ") AND "
				+ kalori + " >0" + alergi + " ORDER BY random() LIMIT 2";

		cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("makan siang : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- snack --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " =" + arrJenis[5] + " AND " + kalori + " >0"
				+ alergi + " ORDER BY random() LIMIT 1";

		cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("snack sore : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- makan malam (makanan pokok) --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " IN (" + arrJenis[1] + "," + arrJenis[2] + ") AND "
				+ kalori + " >0" + alergi + " ORDER BY random() LIMIT 1";

		cursor = db.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("makan malam : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		// ----------------- makan malam (lauk + sayur) --------------------
		query = "SELECT DISTINCT " + cols + " FROM " + tabelMakanan + " WHERE "
				+ jenis + " IN (" + arrJenis[1] + "," + arrJenis[2] + ") AND "
				+ kalori + " >0" + alergi + " ORDER BY random() LIMIT 2";

		cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setPorsi(cursor.getString(2));
				makanan.setBobot(cursor.getInt(3));
				makananList.add(makanan);
				System.out.println("makan malam : " + makanan.getNama());

			} while (cursor.moveToNext());
		}
		cursor.close();

		db.close();
		return makananList;
	}

	public List<Makanan> getPerJenis(String jenis) {
		List<Makanan> makananList = new ArrayList<Makanan>();
		String selectQuery = "SELECT  * FROM " + tabelMakanan
				+ " WHERE JENIS = " + "'" + jenis + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(cursor.getInt(1));
				makanan.setProtein(cursor.getDouble(2));
				makanan.setKarbohidrat(cursor.getDouble(3));
				makanan.setLemak(cursor.getDouble(4));
				makanan.setNatrium(cursor.getDouble(5));
				makanan.setPorsi(cursor.getString(6));
				makanan.setBobot(cursor.getInt(7));
				makanan.setRating(cursor.getFloat(8));
				makanan.setJenisMakanan(cursor.getString(9));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(10));
				int s = Integer.parseInt(cursor.getString(11));
				int k = Integer.parseInt(cursor.getString(12));

				makanan.setHewani(h == 1);
				makanan.setSeafood(s == 1);
				makanan.setKacang(k == 1);
				makanan.setTerakhirDipilih(cursor.getLong(13));
				makanan.setPathFoto(cursor.getString(14));

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
		int[] count = new int[arrJenis.length];

		SQLiteDatabase db = this.getReadableDatabase();
		for (int i = 0; i < arrJenis.length; i++) {
			String countQuery = "SELECT  * FROM " + tabelMakanan + " WHERE "
					+ jenis + " = " + arrJenis[i];
			Cursor cursor = db.rawQuery(countQuery, null);
			count[i] = cursor.getCount();
			cursor.close();
		}
		db.close();

		return count;
	}

	public boolean updateRating(String nama, float rating) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(this.rating, rating);

		boolean b = db.update(tabelMakanan, values, KEY_ID + " = ?",
				new String[] { String.valueOf(nama) }) > 0;
		db.close();
		return b;
	}
}
