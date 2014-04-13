package dao;

import model.Pengguna;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//code source from here : http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DatabaseHandlerProfil extends SQLiteOpenHelper {
	private static DatabaseHandlerProfil sInstance;
	// All Static variables
	// Database Version
	private static final int versiDB = 1;

	// Database Name
	private static final String namaDB = "gamma.db";
	private static final String KEY_ID = "id";

	// Nama tabel yang akan dibuat
	private static final String tabelProfil = "profil";

	// Kolom tabel profil
	private static final String id = "id";
	private static final String nama = "nama";
	private static final String umur = "umur";
	private static final String berat = "berat";
	private static final String tinggi = "tinggi";
	private static final String target = "target";
	private static final String gender = "gender";
	private static final String gayaHidup = "gayaHidup";
	private static final String kacang = "kacang";
	private static final String seafood = "seafood";
	private static final String hewani = "hewani";

	public static DatabaseHandlerProfil getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new DatabaseHandlerProfil(context);
		}
		return sInstance;
	}

	private DatabaseHandlerProfil(Context context) {
		super(context, namaDB, null, versiDB);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
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
		db.execSQL("DROP TABLE IF EXISTS " + tabelProfil);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
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