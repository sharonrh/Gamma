package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import model.Notifikasi;
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
				+ "lemak REAL, natrium REAL, porsi TEXT, bobot INTEGER, rating INTEGER, jenis TEXT, hewani INTEGER, "
				+ "seafood INTEGER, kacang INTEGER, terakhirDipilih INTEGER, pathFoto TEXT, waktuBaik INTEGER, kombinasi INTEGER);";
		db.execSQL(buatTabelMakanan);
		// tabel laporan
		String buatTabelLaporan = "CREATE TABLE laporan (id INTEGER PRIMARY KEY AUTOINCREMENT, waktu INTEGER, berat REAL, tinggi REAL);";
		db.execSQL(buatTabelLaporan);
		// tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE notifikasi (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, waktu INTEGER, pesan TEXT, selected INTEGER);";
		db.execSQL(buatTabelNotifikasi);
		// tabel profil
		String buatTabelProfil = "CREATE TABLE profil (id INTEGER PRIMARY KEY, nama TEXT, umur INTEGER, berat REAL, tinggi REAL, target REAL, gender INTEGER, "
				+ "gayaHidup INTEGER, kacang INTEGER, seafood INTEGER, vegetarian INTEGER, foto TEXT, startTime REAL, endTime REAL);";
		db.execSQL(buatTabelProfil);
		// tabel achievement
		String buatTabelAchievement = "CREATE TABLE achievement (nama TEXT PRIMARY KEY, terkunci INTEGER, deskripsi TEXT, progress INTEGER, requirement INTEGER, "
				+ "pathLogo TEXT);";
		db.execSQL(buatTabelAchievement);
		// tabel rekomendasi
		String buatTabelRekomendasi = "CREATE TABLE rekomendasi (nama TEXT PRIMARY KEY, kalori INTEGER, porsi INTEGER, bobot INTEGER);";
		db.execSQL(buatTabelRekomendasi);

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
		tambahNotifikasiDefault(db);

		// baca data makanan
		bacaFile(c, db);

		// baca data untuk achievement
		initAch(db);

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
				values.put("waktuBaik", Integer.parseInt(temp[15]));
				values.put("kombinasi", Integer.parseInt(temp[16]));

				db.insert("makanan", null, values);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tambahNotifikasiDefault(SQLiteDatabase db) {

		Calendar calendar = Calendar.getInstance();
		// 9 AM
		calendar.set(Calendar.HOUR_OF_DAY, 7);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Notifikasi sarapan = new Notifikasi("Sarapan",
				calendar.getTimeInMillis(), "Saatnya sarapan!", false);

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		Notifikasi makanSiang = new Notifikasi("Makan Siang",
				calendar.getTimeInMillis(), "Saatnya makan siang!", false);

		calendar.set(Calendar.HOUR_OF_DAY, 19);
		Notifikasi makanMalam = new Notifikasi("Makan Malam",
				calendar.getTimeInMillis(), "Woi, Makan Malam!", false);

		calendar.set(Calendar.HOUR_OF_DAY, 10);
		Notifikasi snack1 = new Notifikasi("Snack 1", calendar.getTimeInMillis(), "Waktunya Kamu Makan Snack!", false);
		
		calendar.set(Calendar.HOUR_OF_DAY, 15);
		Notifikasi snack2 = new Notifikasi("Snack 2", calendar.getTimeInMillis(), "Waktunya Kamu Makan Snack!", false);
		
		tambahNotifikasi(sarapan, db);
		tambahNotifikasi(makanSiang, db);
		tambahNotifikasi(makanMalam, db);
		tambahNotifikasi(snack1, db);
		tambahNotifikasi(snack2, db);
	}

	public boolean tambahNotifikasi(Notifikasi notifikasi, SQLiteDatabase db) {

		ContentValues values = new ContentValues();
		values.put("nama", notifikasi.getNama());
		values.put("waktu", notifikasi.getWaktu());
		values.put("pesan", notifikasi.getPesan());

		// Inserting Row
		boolean cek = db.insert("notifikasi", null, values) > 0;
		return cek;
	}

	public void initAch(SQLiteDatabase db) {
		ArrayList<String> nama = new ArrayList<String>();
		ArrayList<String> deskripsi = new ArrayList<String>();
		ArrayList<String> pathLogo = new ArrayList<String>();
		int[] requirement = new int[4];

		nama.add("Starter");
		nama.add("Halfway");
		nama.add("Finisher");
		nama.add("Bookworm");
		deskripsi.add("Mencapai 25% dari target diet.");
		deskripsi.add("Mencapai 50% dari target diet.");
		deskripsi.add("Mencapai 100% dari target diet.");
		deskripsi.add("Membaca 10 artikel.");

		// set logo
		pathLogo.add("logo/locked-starter.jpg");
		pathLogo.add("logo/locked-halfway.jpg");
		pathLogo.add("logo/locked-finisher.jpg");
		pathLogo.add("logo/locked-bookworm.jpg");

		requirement[0] = 25;
		requirement[1] = 50;
		requirement[2] = 100;
		requirement[3] = 2;
		ContentValues content = new ContentValues();
		for (int i = 0; i < 4; i++) {
			content.put("nama", nama.get(i));
			content.put("terkunci", 0);
			content.put("deskripsi", deskripsi.get(i));
			content.put("progress", 0);
			content.put("requirement", requirement[i]);
			content.put("pathLogo", pathLogo.get(i));
			db.insert("achievement", null, content);
		}
	}

	public void resetProgress(Context c, SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS laporan");
		db.execSQL("DROP TABLE IF EXISTS notifikasi");
		db.execSQL("DROP TABLE IF EXISTS profil");
		db.execSQL("DROP TABLE IF EXISTS achievement");

		// buat database kembali
		// tabel laporan
		String buatTabelLaporan = "CREATE TABLE laporan (id INTEGER PRIMARY KEY AUTOINCREMENT, waktu INTEGER, berat REAL, tinggi REAL);";
		db.execSQL(buatTabelLaporan);
		// tabel notifikasi
		String buatTabelNotifikasi = "CREATE TABLE notifikasi (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, waktu INTEGER, pesan TEXT, selected INTEGER);";
		db.execSQL(buatTabelNotifikasi);
		// tabel profil
		String buatTabelProfil = "CREATE TABLE profil (id INTEGER PRIMARY KEY, nama TEXT, umur INTEGER, berat REAL, tinggi REAL, target REAL, gender INTEGER, "
				+ "gayaHidup INTEGER, kacang INTEGER, seafood INTEGER, vegetarian INTEGER, foto TEXT, startTime REAL, endTime REAL);";
		db.execSQL(buatTabelProfil);
		// tabel achievement
		String buatTabelAchievement = "CREATE TABLE achievement (nama TEXT PRIMARY KEY, terkunci INTEGER, deskripsi TEXT, progress INTEGER, requirement INTEGER, "
				+ "pathLogo TEXT);";
		db.execSQL(buatTabelAchievement);
		// tabel rekomendasi
		String buatTabelRekomendasi = "CREATE TABLE rekomendasi (nama TEXT PRIMARY KEY, kalori INTEGER, porsi INTEGER, bobot INTEGER);";
		db.execSQL(buatTabelRekomendasi);

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

		tambahNotifikasiDefault(db);

		initAch(db); // re-populate achievement di database
	}
}
