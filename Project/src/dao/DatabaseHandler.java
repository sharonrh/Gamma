package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
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
		//tabel makanan
		String buatTabelMakanan = "CREATE TABLE makanan (nama TEXT PRIMARY KEY, kalori INTEGER, protein REAL, " +
				"lemak REAL, karbohidrat REAL, kalsium REAL, rating INTEGER, persentase INTEGER, jenis TEXT, hewani INTEGER, " +
				"seafood INTEGER, kacang INTEGER, terakhirDipilih INTEGER);";
		db.execSQL(buatTabelMakanan);
		//tabel laporan
		String buatTabelLaporan = "CREATE TABLE laporan (id INTEGER PRIMARY KEY AUTOINCREMENT, waktu INTEGER, berat REAL, tinggi REAL);";
		db.execSQL(buatTabelLaporan);
		//tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE notifikasi (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, waktu INTEGER, pesan TEXT);";
		db.execSQL(buatTabelNotifikasi);
		//tabel profil
		String buatTabelProfil = "CREATE TABLE profil (id INTEGER PRIMARY KEY, nama TEXT, umur INTEGER, berat REAL, tinggi REAL, target REAL, gender INTEGER, " +
				"gayaHidup INTEGER, kacang INTEGER, seafood INTEGER, hewani INTEGER);";
		db.execSQL(buatTabelProfil);
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
