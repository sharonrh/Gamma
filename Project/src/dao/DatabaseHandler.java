package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";

	public DatabaseHandler(Context context) {
		super(context, namaDB, null, versiDB);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// tabel makanan
		String buatTabelMakanan = "CREATE TABLE makanan (nama TEXT PRIMARY KEY, kalori INTEGER, protein REAL, "
				+ "lemak REAL, karbohidrat REAL, kalsium REAL, rating INTEGER, persentase INTEGER, jenis TEXT, hewani INTEGER, "
				+ "seafood INTEGER, kacang INTEGER, terakhirDipilih INTEGER);";
		db.execSQL(buatTabelMakanan);
		// tabel laporan
		String buatTabelLaporan = "CREATE TABLE laporan (id INTEGER PRIMARY KEY AUTOINCREMENT, waktu INTEGER, berat REAL, tinggi REAL);";
		db.execSQL(buatTabelLaporan);
		// tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE notifikasi (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, waktu INTEGER, pesan TEXT);";
		db.execSQL(buatTabelNotifikasi);
		// tabel profil
		String buatTabelProfil = "CREATE TABLE profil (id INTEGER PRIMARY KEY, nama TEXT, umur INTEGER, berat REAL, tinggi REAL, target REAL, gender INTEGER, "
				+ "gayaHidup INTEGER, kacang INTEGER, seafood INTEGER, hewani INTEGER);";
		db.execSQL(buatTabelProfil);

		// isi tabel profil dengan data awal
		ContentValues values = new ContentValues();
		values.put("nama", "");
		values.put("umur", 0);
		values.put("berat", 0);
		values.put("tinggi", 0);
		values.put("target", 0);
		values.put("gender", 0);
		values.put("gayaHidup", 0);
		values.put("kacang", 0);
		values.put("seafood", 0);
		values.put("hewani", 0);
		// Inserting Row
		db.insert("profil", null, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS makanan");
		db.execSQL("DROP TABLE IF EXISTS laporan");
		db.execSQL("DROP TABLE IF EXISTS notifikasi");
		db.execSQL("DROP TABLE IF EXISTS profil");
		// Create tables again
		onCreate(db);

	}

}
