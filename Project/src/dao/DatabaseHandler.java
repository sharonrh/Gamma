package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Makanan;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";

	private Context c;

	public DatabaseHandler(Context context) {
		super(context, namaDB, null, versiDB);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// tabel makanan
		String buatTabelMakanan = "CREATE TABLE makanan (nama TEXT PRIMARY KEY, kalori INTEGER, protein REAL, karbohidrat REAL, "
				+ "lemak REAL, natrium REAL, porsi INTEGER, bobot INTEGER, rating INTEGER, jenis TEXT, hewani INTEGER, "
				+ "seafood INTEGER, kacang INTEGER, terakhirDipilih INTEGER, pathFoto TEXT);";
		db.execSQL(buatTabelMakanan);
		// tabel laporan
		String buatTabelLaporan = "CREATE TABLE laporan (id INTEGER PRIMARY KEY AUTOINCREMENT, waktu INTEGER, berat REAL, tinggi REAL);";
		db.execSQL(buatTabelLaporan);
		// tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE notifikasi (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, waktu INTEGER, pesan TEXT);";
		db.execSQL(buatTabelNotifikasi);
		// tabel profil
		String buatTabelProfil = "CREATE TABLE profil (id INTEGER PRIMARY KEY, nama TEXT, umur INTEGER, berat REAL, tinggi REAL, target REAL, gender INTEGER, "
				+ "gayaHidup INTEGER, kacang INTEGER, seafood INTEGER, vegetarian INTEGER, foto TEXT, startTime REAL, endTime REAL);";
		db.execSQL(buatTabelProfil);
		// tabel achievement
		String buatTabelAchievement = "CREATE TABLE achievement (nama TEXT PRIMARY KEY, terkunci INTEGER, deskripsi TEXT, progress REAL, pathLogo TEXT);";
		db.execSQL(buatTabelAchievement);

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
		values.put("vegetarian", 0);
		values.put("foto", "");
		values.put("startTime", 0);
		values.put("endTime", 0);

		// Inserting Row
		db.insert("profil", null, values);
		// baca data makanan
		bacaFile(c, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS makanan");
		db.execSQL("DROP TABLE IF EXISTS laporan");
		db.execSQL("DROP TABLE IF EXISTS notifikasi");
		db.execSQL("DROP TABLE IF EXISTS profil");
		db.execSQL("DROP TABLE IF EXISTS achievement");
		// Create tables again
		onCreate(db);
	}

	private void bacaFile(Context context, SQLiteDatabase db) {
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

				int isHewani = temp[10].charAt(0) == 'Y' ? 1 : 0;
				int isSeafood = temp[11].charAt(0) == 'Y' ? 1 : 0;
				int isKacang = temp[12].charAt(0) == 'Y' ? 1 : 0;

				ContentValues values = new ContentValues();
				values.put("nama", temp[0]);
				values.put("kalori", Integer.parseInt(temp[1]));
				values.put("protein", Double.parseDouble(temp[2]));
				values.put("karbohidrat", Double.parseDouble(temp[3]));
				values.put("lemak", Double.parseDouble(temp[4]));
				values.put("natrium", Double.parseDouble(temp[5]));
				values.put("porsi", temp[6]);
				values.put("bobot", Integer.parseInt(temp[7]));
				values.put("rating", Integer.parseInt(temp[8]));
				values.put("jenis", temp[9]);
				values.put("hewani", isHewani);
				values.put("seafood", isSeafood);
				values.put("kacang", isKacang);
				values.put("terakhirDipilih", Long.parseLong(temp[13]));
				values.put("pathFoto", temp[14]);

				db.insert("makanan", null, values);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initAchievement(Context context, SQLiteDatabase db) {
		AssetManager am = context.getAssets();
		InputStream is;
		try {
			is = am.open("achievement.csv");

			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader reader = new BufferedReader(isr);
			reader.readLine(); // baca header
			String line;

			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");

				ContentValues values = new ContentValues();
				values.put("nama", temp[0]);
				values.put("terkunci", Integer.parseInt(temp[1]));
				values.put("deskripsi", temp[2]);
				values.put("progress", Double.parseDouble(temp[3]));
				values.put("pathLogo", temp[4]);
				db.insert("achievement", null, values);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
