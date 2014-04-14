package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.*;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code source from here : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DBHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";
	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelMakanan = "makanan";
	private static final String tabelLaporan = "laporan";
	private static final String tabelNotifikasi = "notifikasi";
	private static final String tabelProfil = "profil";

	// Kolom tabel makanan
	private static final String namaMakanan = "namaMakanan";
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
	
	// Kolom tabel laporan
	private static final String id = "ID";
	private static final String waktu = "waktu";
	private static final String berat = "berat";
	private static final String tinggi = "tinggi";
	
	// Kolom tabel notifikasi (beberapa ada yang sama)
	//private static final String id = "id";
	private static final String nama = "nama";
	//private static final String waktu = "waktu";
	private static final String pesan = "pesan";
	
	// Kolom tabel profil (beberapa ada yang sama dengan tabel lainnya)
	//private static final String id = "id";
	//private static final String nama = "nama";
	private static final String umur = "umur";
	//private static final String berat = "berat";
	//private static final String tinggi = "tinggi";
	private static final String target = "target";
	private static final String gender = "gender";
	private static final String gayaHidup = "gayaHidup";
	//private static final String kacang = "kacang";
	//private static final String seafood = "seafood";
	//private static final String hewani = "hewani";
	
	public DBHelper(Context context) {
		super(context, namaDB, null, versiDB);
		bacaFile(context);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		//tabel makanan
		String buatTabelMakanan = "CREATE TABLE " + tabelMakanan + "("
				+ namaMakanan + " TEXT PRIMARY KEY," + jlhKalori + " INTEGER,"
				+ jlhProtein + " REAL," + jlhLemak + " REAL," + jlhKarbo
				+ " REAL," + jlhKalsium + " REAL," + rating + " INTEGER,"
				+ persentase + " REAL," + jenis + " TEXT" + hewani
				+ " INTEGER," + seafood + " INTEGER," + kacang + " INTEGER,"
				+ terakhir + " INTEGER" + ");";
		db.execSQL(buatTabelMakanan);
		//tabel laporan
		String buatTabelLaporan = "CREATE TABLE " + tabelLaporan + "(" + id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + waktu + " INTEGER,"
				+ berat + " REAL," + tinggi + " REAL" + ")";
		db.execSQL(buatTabelLaporan);
		//tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE " + tabelNotifikasi + "("
				+ id + " INTEGER PRIMARY KEY AUTOINCREMENT," + nama + " TEXT,"
				+ waktu + " INTEGER," + pesan + " TEXT" + ")";
		db.execSQL(buatTabelNotifikasi);
		//tabel profil
		String buatTabelProfil = "CREATE TABLE " + tabelProfil + "(" + id
				+ " INTEGER PRIMARY KEY," + nama + " TEXT," + umur
				+ " INTEGER," + berat + " REAL," + tinggi + " REAL," + target
				+ " REAL," + gender + " INTEGER," + gayaHidup + " INTEGER,"
				+ kacang + " INTEGER," + seafood + " INTEGER," + hewani
				+ " INTEGER" + ")";
		db.execSQL(buatTabelProfil);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + tabelMakanan);
		db.execSQL("DROP TABLE IF EXISTS " + tabelLaporan);
		db.execSQL("DROP TABLE IF EXISTS " + tabelNotifikasi);
		db.execSQL("DROP TABLE IF EXISTS " + tabelProfil);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 * untuk tabel Makanan
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
		db.close(); // Closing database connection
	}

	// Getting single makanan
	Makanan getMakanan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelMakanan, new String[] { namaMakanan,
				jlhKalori, jlhProtein, jlhLemak, jlhKarbo, jlhKalsium, rating,
				persentase, jenis, hewani, seafood, kacang, terakhir },
				namaMakanan + "=?", new String[] { String.valueOf(nama) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Makanan makanan = new Makanan(cursor.getString(0),
				Integer.parseInt(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)),
				Double.parseDouble(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor
						.getString(7)), cursor.getString(8), 100);

		// retrieve data alegi
		int h = Integer.parseInt(cursor.getString(9));
		int s = Integer.parseInt(cursor.getString(10));
		int k = Integer.parseInt(cursor.getString(11));

		if (h == 1) {
			makanan.setHewani(true);
		}
		if (s == 1) {
			makanan.setSeafood(true);
		}
		if (k == 1) {
			makanan.setKacang(true);
		}
		makanan.setTerakhir(Integer.parseInt(cursor.getString(12)));

		// return makanan
		return makanan;
	}

	// Getting All Makanan
	public List<Makanan> getAllMakanan() {
		List<Makanan> makananList = new ArrayList<Makanan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelMakanan;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Makanan makanan = new Makanan();
				makanan.setNama(cursor.getString(0));
				makanan.setKalori(Integer.parseInt(cursor.getString(1)));
				makanan.setProtein(Double.parseDouble(cursor.getString(2)));
				makanan.setKarbohidrat(Double.parseDouble(cursor.getString(3)));
				makanan.setLemak(Double.parseDouble(cursor.getString(4)));
				makanan.setKalsium(Double.parseDouble(cursor.getString(5)));
				makanan.setPersentase(Integer.parseInt(cursor.getString(6)));
				makanan.setRating(Integer.parseInt(cursor.getString(7)));
				makanan.setJenisMakanan(cursor.getString(8));

				// retrieve data alegi
				int h = Integer.parseInt(cursor.getString(9));
				int s = Integer.parseInt(cursor.getString(10));
				int k = Integer.parseInt(cursor.getString(11));

				if (h == 1) {
					makanan.setHewani(true);
				}
				if (s == 1) {
					makanan.setSeafood(true);
				}
				if (k == 1) {
					makanan.setKacang(true);
				}
				makanan.setTerakhir(Integer.parseInt(cursor.getString(12)));

				// Adding makanan to list
				makananList.add(makanan);
			} while (cursor.moveToNext());
		}

		// return makanan list
		return makananList;
	}

	// Updating single makanan
	public int updateMakanan(Makanan makanan) {
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

		// updating row
		return db.update(tabelMakanan, values, KEY_ID + " = ?",
				new String[] { String.valueOf(makanan.getNama()) });
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

	// Baca data dari csv

	public ArrayList<Makanan> bacaFile(Context context) {
		ArrayList<Makanan> mk = new ArrayList<Makanan>();
		AssetManager am = context.getAssets();
		InputStream is;
		try {
			is = am.open("data_makanan.csv");

			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader reader = new BufferedReader(isr);
			reader.readLine(); // baca header
			String line;

			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				boolean isHewani = false, isSeafood = false, isKacang = false;
				if (temp[9].charAt(0) == 'Y') {
					isHewani = true;
				}
				if (temp[10].charAt(0) == 'Y') {
					isSeafood = true;
				}
				if (temp[11].charAt(0) == 'Y') {
					isKacang = true;
				}
				Makanan ma = new Makanan(temp[0], Integer.parseInt(temp[1]),
						Double.parseDouble(temp[2]),
						Double.parseDouble(temp[3]),
						Double.parseDouble(temp[4]),
						Double.parseDouble(temp[5]), Integer.parseInt(temp[6]),
						Integer.parseInt(temp[7]), temp[8],
						Integer.parseInt(temp[12]));
				mk.add(ma);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mk;
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 * untuk tabel Laporan
	 */
	
	public boolean tambahLaporan(long waktuInput, String beratInput,
			String tinggiInput) {
		SQLiteDatabase db = this.getWritableDatabase();

		System.out.println("size: " + getLaporanCount());
		ContentValues values = new ContentValues();
		values.put(waktu, waktuInput);
		values.put(berat, beratInput);
		values.put(tinggi, tinggiInput);

		Cursor cursor = db.rawQuery("SELECT waktu  FROM " + tabelLaporan, null);

		// pastiin dia ga kosong
		if (cursor.moveToLast()) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(waktuInput);
			Calendar cl = Calendar.getInstance();
			c.setTimeInMillis(Long.parseLong(cursor.getString(0)));
			// kalo tanggalnya sama, update data yang lama
			if (c.get(Calendar.YEAR) == cl.get(Calendar.YEAR)
					&& c.get(Calendar.MONTH) == cl.get(Calendar.MONTH)
					&& c.get(Calendar.DATE) == cl.get(Calendar.DATE)) {
				return db.update(tabelLaporan, values, KEY_ID + " = ?",
						new String[] { String.valueOf(cursor.getCount()) }) > 0;
			}
		}
		return db.insert(tabelLaporan, null, values) > 0;
	}

	// Getting single laporan
	public Laporan getLaporan(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelLaporan, new String[] { id, waktu, berat,
				tinggi }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Laporan laporan = new Laporan(Long.parseLong(cursor.getString(1)),
				Double.parseDouble(cursor.getString(2)),
				Double.parseDouble(cursor.getString(3)));

		return laporan;
	}

	// Getting All Laporan
	public List<Laporan> getAllLaporan() {
		List<Laporan> laporanList = new ArrayList<Laporan>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + tabelLaporan;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Laporan laporan = new Laporan();
				laporan.setWaktu(Long.parseLong(cursor.getString(1)));
				laporan.setBeratBadan(Double.parseDouble(cursor.getString(2)));
				laporan.setTinggiBadan(Double.parseDouble(cursor.getString(3)));

				// Adding laporan to list
				laporanList.add(laporan);
			} while (cursor.moveToNext());
		}

		// return laporan list
		return laporanList;
	}

	// Deleting single laporan
	public void deleteLaporan(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelLaporan, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// Getting laporan count
	public int getLaporanCount() {
		String countQuery = "SELECT  * FROM " + tabelLaporan;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();

		// return count
		return cursor.getCount();
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 * untuk tabel Notifikasi
	 */
	public boolean tambahNotifikasi(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(nama, notifikasi.getNama());
		values.put(waktu, notifikasi.getWaktu());
		values.put(pesan, notifikasi.getPesan());

		// Inserting Row
		boolean cek = db.insert(tabelNotifikasi, null, values) > 0;
		db.close(); // Closing database connection
		return cek;
	}

	// Getting single notifikasi
	Notifikasi getNotifikasi(String nama) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(tabelNotifikasi, new String[] { id, nama,
				waktu, pesan }, KEY_ID + "=?",
				new String[] { String.valueOf(nama) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Notifikasi notifikasi = new Notifikasi(cursor.getString(1),
				Long.parseLong(cursor.getString(2)), cursor.getString(3));

		// return notifikasi
		return notifikasi;
	}

	// Getting All Notifications
	public List<Notifikasi> getAllNotifikasi() {
		List<Notifikasi> notifikasiList = new ArrayList<Notifikasi>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + tabelNotifikasi;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Notifikasi notifikasi = new Notifikasi();
				notifikasi.setNama(cursor.getString(1));
				notifikasi.setWaktu(Long.parseLong(cursor.getString(2)));
				notifikasi.setPesan(cursor.getString(3));

				// Adding notifikasi to list
				notifikasiList.add(notifikasi);
			} while (cursor.moveToNext());
		}

		// return notifikasi list
		return notifikasiList;
	}

	// Deleting single notifikasi
	public void deleteNotifikasi(Notifikasi notifikasi) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabelNotifikasi, KEY_ID + " = ?",
				new String[] { String.valueOf(nama) });
		db.close();
	}

	// Getting notifikasis Count
	public int getNotifikasiCount() {
		String countQuery = "SELECT  * FROM " + tabelNotifikasi;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 * untuk tabel Profil
	 */
	// Adding new profil
		public boolean tambahProfil(String namaNew, int umurNew, double beratNew,
				double tinggiNew, double targetNew, char genderNew,
				int gayaHidupNew, boolean isAlergiKacang, boolean isAlergiSeafood,
				boolean isVegetarian) {
			SQLiteDatabase db = this.getWritableDatabase();
			int h = 0, s = 0, k = 0, g = 0;
			ContentValues values = new ContentValues();
			values.put(nama, namaNew);
			values.put(umur, umurNew);
			values.put(berat, beratNew);
			values.put(tinggi, tinggiNew);
			values.put(target, targetNew);
			if (genderNew == 'l') {
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

			// Inserting Row
			return db.insert(tabelProfil, null, values) > 0;
		}

		// Getting single profil
		public Pengguna getProfil() {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(tabelProfil, new String[] { id, nama, umur,
					berat, tinggi, target, gender, gayaHidup, kacang, seafood,
					hewani }, id + "=?", new String[] { String.valueOf(1) }, null,
					null, null, null);
			if (cursor != null)
				cursor.moveToFirst();

			Pengguna profil = new Pengguna(cursor.getString(0),
					Integer.parseInt(cursor.getString(1)),
					Double.parseDouble(cursor.getString(2)),
					Double.parseDouble(cursor.getString(3)),
					Double.parseDouble(cursor.getString(4)), cursor.getString(5)
							.charAt(0), Integer.parseInt(cursor.getString(6)),
					false, false, false);

			// retrieve data alegi
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
				boolean isVegetarian) {
			SQLiteDatabase db = this.getWritableDatabase();
			int h = 0, s = 0, k = 0, g = 0;
			ContentValues values = new ContentValues();
			values.put(nama, namaNew);
			values.put(umur, umurNew);
			values.put(berat, beratNew);
			values.put(tinggi, tinggiNew);
			values.put(target, targetNew);
			if (genderNew == 'l') {
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